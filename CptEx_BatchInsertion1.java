package p2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


class EmployeeSalary {

    private int empId;
    private String empName;
    private double salary;
    private String status;

    public EmployeeSalary(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
        this.status = "PENDING";
    }

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}



public class CptEx_BatchInsertion1 {

    private static final int BATCH_SIZE = 2; // small for learning

    public static void main(String[] args) {

        List<EmployeeSalary> salaries = List.of(
            new EmployeeSalary(101, "Arun", 5000),
            new EmployeeSalary(102, "Kumar", 2500), // invalid
            new EmployeeSalary(103, "Ben", 7000),
            new EmployeeSalary(104, "Thiyagu", 4500)
        );

        String sql = "INSERT INTO employee_salary(emp_id, emp_name, salary, status)VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/database1",
                "postgres",
                "root")) {

            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sql)) {

                int count = 0;

                for (EmployeeSalary emp : salaries) {

                    // 1️⃣ Business validation
                    validate(emp);

                    // 2️⃣ Prepare batch record
                    ps.setInt(1, emp.getEmpId());
                    ps.setString(2, emp.getEmpName());
                    ps.setDouble(3, emp.getSalary());
                    ps.setString(4, "PROCESSED");
                    ps.addBatch();

                    count++;

                    // 3️⃣ Batch size control read last after program i will tell why and what is batch size contorl.
                    if (count % BATCH_SIZE == 0) {
                        ps.executeBatch();
                    }
                }

                ps.executeBatch(); // remaining records
                con.commit();

                System.out.println("Payroll batch processed successfully");

            } catch (Exception e) {
                con.rollback();
                System.out.println("Payroll batch failed. Rolled back.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void validate(EmployeeSalary emp) {
        if (emp.getSalary() < 3000) {
            throw new IllegalArgumentException(
                "Salary below minimum for empId " + emp.getEmpId()
            );
        }
    }
}
/**
 * 1️⃣ What “batch size control” actually means
The line you’re asking about
if (count % BATCH_SIZE == 0) {
    ps.executeBatch();
}

This does NOT mean:

Insert only 2 records

Or stop processing

It means:

“Every time I collect BATCH_SIZE number of records,
send them to the database now, instead of waiting.”

So batch size control = send data in chunks.

Without batch size control (dangerous version)

Imagine this code:

for (EmployeeSalary emp : salaries) {
    ps.addBatch();
}
ps.executeBatch();

Now imagine:

salaries contains 50,000 employees

What happens internally?

JDBC stores 50,000 SQL operations in memory

Database receives 50,000 operations at once

Database locks table for a long time

Memory usage spikes

If one record fails → everything fails after a long wait

This is how systems:

Slow down

Freeze

Crash at month-end payroll

2️⃣ What batch size control protects you from (REAL reasons)

Let’s be very concrete.

🚨 Problem 1: Memory explosion

Each addBatch():

Stores SQL + parameters in memory

10 records → fine
10,000 records → heavy
100,000 records → 💥

Batch size control limits:

“Never hold more than X records in memory.”

🚨 Problem 2: Database locking

Databases lock:

Rows

Indexes

Sometimes entire tables

Big batch → long lock →
Other users:

Can’t read

Can’t write

System feels “hung”

Small batches → short locks →
System stays responsive.

🚨 Problem 3: Failure blast radius

Imagine payroll:

Employee #49,999 has bad data

Entire 50,000 batch fails

That’s:

Wasted time

Hard debugging

Angry finance team

Batch size control limits damage scope.

3️⃣ What the code is actually doing (line by line)

Let’s replay your code slowly.

Assume:

BATCH_SIZE = 2;

And salaries list has 4 employees.

Iteration 1

count = 1

addBatch()

1 % 2 != 0 → ❌ no execute

Batch buffer:

[ emp1 ]
Iteration 2

count = 2

addBatch()

2 % 2 == 0 → ✅ executeBatch()

Database receives:

emp1, emp2

Batch buffer cleared automatically.

Iteration 3

count = 3

addBatch()

3 % 2 != 0 → ❌ no execute

Batch buffer:

[ emp3 ]
Iteration 4

count = 4

addBatch()

4 % 2 == 0 → ✅ executeBatch()

Database receives:

emp3, emp4
After loop ends
ps.executeBatch(); // remaining records

Why needed?

Because if total count is not a multiple of batch size,
some records will still be waiting.

This line ensures:

“Send whatever is left.”

4️⃣ Does batch size change output/result?
Important truth

NO — it does NOT change the final data result.

Whether you:

Insert 1000 records at once
or

Insert 500 + 500

The final table is identical.

What changes?
Aspect	Without batch size	With batch size
Memory usage	❌ High	✅ Controlled
DB lock time	❌ Long	✅ Short
Failure impact	❌ Massive	✅ Limited
Scalability	❌ Poor	✅ Good
Production safety	❌ Risky	✅ Safe
5️⃣ Why this is mandatory in payroll & billing

Payroll & billing systems:

Process thousands / millions of records

Run on shared databases

Must not block other operations

Batch size control ensures:

Finance runs finish on time

Systems don’t go down

Nightly jobs don’t affect daytime users

This is non-negotiable in real companies.

6️⃣ Simple analogy (lock it in your head)

Imagine moving stones:

❌ Carry 100 stones at once → fall down

✅ Carry 10 stones, 10 trips → reach safely

Batch size = how many stones you carry per trip

Same destination.
Different safety.

7️⃣ When should YOU use batch size control?

Always use it when:

Processing lists

Handling payroll / billing

Running scheduled jobs

Writing to DB in bulk

Using JDBC batch

The only time you don’t care:

< 50 records

Toy programs

Learning demos

You are past that stage now.

Final sentence (remember this)

Batch size control does not change what you insert.
It changes how safely and efficiently you insert it.
 * /
 */