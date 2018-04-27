package mysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Formatter;;

public class App {

	// insert doctor
	public static void insertDoctor( )
	{ 
		int id;
		String name = null ,type = null;
		System.out.println("hello DR. - insert your id here : ");
		Scanner cr=new Scanner(System.in);
		id=cr.nextInt();
		String[] str=new String[2];
		cr.nextLine();
		for(int i=0;i<str.length;i++)
		{
			if(i==0) System.out.println("insert your name here : ");
			if(i==1) System.out.println("insert your type here : ");
			str[i]=cr.nextLine();
		}
		cr.close();
		name=str[0];

		type=str[1];

		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost/healthmaintenance", "root", "root")){
			String sql = "INSERT INTO  doctors(doctor_id , doctor_name , doctor_type) " +
					"VALUES ("+id+",'"+ name+"','"+type+"')";
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sql);

			System.out.println(" SUCCESS!\n");
		}
		catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage());
			System.out.println("SQLState: "+ e.getSQLState());
			System.out.println("VendorError: "+e.getErrorCode());
		}
		System.out.println("Thank you for your patronage!");

	}

	// question 1 - select doctor's timetable
	public static void getDoctorTimetable()
	{
		int id;
		System.out.println("hello DR. - insert your id here : ");
		Scanner c=new Scanner(System.in);
		id=c.nextInt();
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost/healthmaintenance", "root", "root")){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT patients.pastient_name , queue_reserved.queue_time FROM patients"+
					" INNER JOIN queue_reserved ON patients.pastient_id=queue_reserved.pastient_id where doctor_id=" 
					+  id  +" order by queue_time");
			int numOfColumns = rs.getMetaData().getColumnCount();
			while (rs.next()){
				for (int col = 1; col <= numOfColumns; col++){
					System.out.format("%16s%10s%16s%16s", rs.getMetaData().getColumnName(col) ,":",rs.getString(col)," | ");
					//System.out.print(rs.getMetaData().getColumnName(col)+" : "+rs.getString(col) + " | ");
				}
				System.out.println();
			}

		}


		catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage());
			System.out.println("SQLState: "+ e.getSQLState());
			System.out.println("VendorError: "+e.getErrorCode());
		}
	}

	// question 2 - update current queue time for pastient by pastient's name
	public static void updateQueue()
	{

		/*  stored procedure :
		 * CREATE DEFINER=`root`@`localhost` PROCEDURE `update_queue`(IN stName varchar(50))
		 * BEGIN  
		 * update `healthmaintenance`.`queue` set  
		 * `time` = current_time()
		 * WHERE `queue_id` in (select queue_id from queue_reserved where pastient_id in
		 * (select pastient_id from patients where pastient_name=stName));
		 * END
		 */
		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost/healthmaintenance", "root", "root")){
			String query = "{CALL update_queue(?,?)}";
			CallableStatement stmt = con.prepareCall(query);
			System.out.println("insert pastient name : ");
			Scanner cs=new Scanner(System.in);
			String patientName = cs.nextLine();
			System.out.println("insert doctor ID : ");
			int  DoctorID=cs.nextInt();
			stmt.setString(2, patientName);
			stmt.setInt(1, DoctorID);
			cs.close();
		 stmt.executeQuery();
			System.out.println(" SUCCESS!\n");
		}
		catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage());
			System.out.println("SQLState: "+ e.getSQLState());
			System.out.println("VendorError: "+e.getErrorCode());
		}

	}

	// question 3 - view top 10 patients who wait the longest time
	public static void TenMaxPastients()
	{
		/* view : 
		  CREATE VIEW Current_Pastients_List AS
		(select pastient_id,queue_time,time,doctor_id FROM queue inner join queue_reserved where queue_reserved.queue_id=queue.queue_id ORDER BY 
				TIMEDIFF(`healthmaintenance`.`queue`.`time`, `healthmaintenance`.`queue_reserved`.`queue_time`) DESC limit 10);
		 */


		try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost/healthmaintenance", "root", "root")){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Current_Pastients_List");
			int numOfColumns = rs.getMetaData().getColumnCount();
			while (rs.next()){
				for (int col = 1; col <= numOfColumns; col++){
					System.out.format("%10s%10s%10s%10s", rs.getMetaData().getColumnName(col) ,":",rs.getString(col)," | ");
				}
				System.out.println();
			}

		}


		catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage());
			System.out.println("SQLState: "+ e.getSQLState());
			System.out.println("VendorError: "+e.getErrorCode());
		}
	}

	
	public static void main(String[] args)
	{
		//insertDoctor();
		getDoctorTimetable();
		//updateQueue();
		//TenMaxPastients();

	}

}
