package project;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    static int userIndex;
    private static Scanner scan = new Scanner(System.in);
    
	private static ArrayList<User> users = new ArrayList<>();
	private static String name, password;
	
	private static Clinic clinic = new Clinic();
	
	static String Admin = "Admin";
	static String Doctor = "Doctor";
	static String Patient = "Patient";
	static String Secretary = "Secretary";
	static String separate = "-----------------------------------------------------------------------------";
	static String separate2 = "------------------------------------";
	static String Done = "Done!\n";
	static String selectOption = "\nPlease select an option:";
	static String logOut = "Logged Out...\n";
	static String invalid = "Invalid selection! Please try again...";
	static String error = "Unsuccessfull Operation!";
	static String appointmentList = "Appointments List:";
	static String timeString = "   Time: ";

	public static void init() {
		users.add(new Admin("Haya", "hayapass", "Admin"));
		users.add(new Secretary("Muath", "muathpass", "Secretary"));
		users.add(new Doctor("Abod", "abodpass", "Doctor"));
		users.add(new Patient("Ahmad", "ahmadpass", "Patient"));
		users.add(new Patient("Majd", "majdpass", "Patient"));
        
        Patient patient = (Patient)users.get(3);
        clinic.addAppointment(new Appointment("05/01/2022", "11"), patient);
        patient = (Patient)users.get(3);
		clinic.addAppointment(new Appointment("14/5/2022", "3"), patient);
		
		clinic.addAppointment(new Appointment("02/05/2022", "9"), patient);
		clinic.addAppointment(new Appointment("25/05/2022", "3"), patient);

		clinic.addVisit(new Appointment("02/05/2022", "9"));
		clinic.addVisit(new Appointment("25/05/2022", "3"));	
	}
	
	public static int authenticateUser() {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).name.equalsIgnoreCase(name)) {
                if (users.get(i).pass.equals(password)) {
                	users.get(i).login(name, password);
                    return i;
                }
                else
                    return -1;
            }
        }
        return -1;
    }
	
	public static void main(String[] args) {
		init();
		
		while (true) {
            System.out.println("Enter Username:");
            name = scan.nextLine();
            
            if (name.equalsIgnoreCase("exit"))
            	System.exit(0);

            System.out.println("\nEnter Password:");
            password = scan.nextLine();

            System.out.println();

            userIndex = authenticateUser();

            if (userIndex != -1) {
                if (users.get(userIndex).role.equalsIgnoreCase(Admin))
                	adminActivities(); 
                else if (users.get(userIndex).role.equalsIgnoreCase(Doctor))
                	doctorActivities();             
                else if (users.get(userIndex).role.equalsIgnoreCase(Patient))
                	patientActivities();
                else if (users.get(userIndex).role.equalsIgnoreCase(Secretary))
                	secretaryActivities();     
            }
            
            else if (name.equalsIgnoreCase("exit"))
            	break;

            else
            	System.out.println("The username or password is incorrect. Please try again...\n");
        }
		
	}
	
	public static void adminActivities() {
		while (true) {
			int i;
			int index;
			String newUserName;
			String newPassword;
			String newRole;
			
			System.out.println("Admin Menu");
	        System.out.println(separate);
	        System.out.println("1. Add User");
	        System.out.println("2. Remove User");
	        System.out.println("3. Show User");
	        System.out.println("4. Sign Out");
	        System.out.println(selectOption);
	        int select = scan.nextInt();
	        System.out.println();
	        
	        switch (select) {
	        case 1:
	        	scan.nextLine();
	        	System.out.println("Enter Username:");
	        	newUserName = scan.nextLine();
				System.out.println("Enter Password:\n");
				newPassword = scan.nextLine();
				System.out.println("Enter Role:\n");
				newRole = scan.nextLine();
				if (newRole.equalsIgnoreCase(Admin))
					users.add(new Admin(newUserName, newPassword, newRole));
				else if (newRole.equalsIgnoreCase(Doctor))
					users.add(new Doctor(newUserName, newPassword, newRole));
				else if (newRole.equalsIgnoreCase(Patient))
					users.add(new Patient(newUserName, newPassword, newRole));
				else if (newRole.equalsIgnoreCase(Secretary))
					users.add(new Secretary(newUserName, newPassword, newRole));
				System.out.println(Done);
				break;
				
	        case 2:
	        	System.out.println("User List:");
	            System.out.println(separate2);
	            i = 1;
	            for (User user : users) {
	            	System.out.println(i++ + ". " + user.name + "\t\t" + user.pass + "\t\t" + user.role);
	            }
	            System.out.println();
	            System.out.println("\nPlease select a user:");
	            index = scan.nextInt();
	            if (index > i || index < 1) {
            		System.out.println(invalid);
            		break;
            	}
	            index--;
	            users.remove(index);
	            System.out.println();
	            System.out.println(Done);
	            break;
	                    	
	        case 3:
	        	System.out.println("User List:");
	            System.out.println(separate2);
	            i = 1;
	            for (User user : users) {
	            	System.out.println(i++ + ". " + user.name + "\t\t" + user.pass + "\t\t" + user.role);
	            }
	            System.out.println();
	            break;
	            
	        case 4:
	        	System.out.println(logOut);
	            scan.nextLine();
	            return;
	        	
	        default:
	            System.out.println(invalid);
	            System.out.println();	
	        }
		}
	}
	
	public static void doctorActivities() {
		while (true) {
			int i = 1;
			int index;
			ArrayList<Integer> appIndex = new ArrayList<>();
			
	        System.out.println("Doctor Menu");
	        System.out.println(separate);
	        System.out.println("1. Show Patients List");
	        System.out.println("2. Sign Out");
	        System.out.println(selectOption);
	        int select = scan.nextInt();
	        System.out.println();
	        
	        switch (select) {
	        case 1:
	            System.out.println("Patients List:");
	            System.out.println(separate2);
	            i = 1;
	            appIndex.clear();
	            for (int j = 0; j < clinic.patientsList.size(); j++)
	            	if (clinic.reservationType.get(j) == 1) {
	            		System.out.println(i++ + ". " + clinic.patientsList.get(j).name);
	            		appIndex.add(j);
	            	}
	            if (appIndex.isEmpty()) {
	            	System.out.println("No patients!\n");
	            	break;
	            }
	            System.out.println();
	            System.out.println("\nPlease select a patient:");
	            index = scan.nextInt();
	            if (index > i || index < 1) {
            		System.out.println(invalid);
            		break;
            	}
	            index--;
	            index = appIndex.get(index);
	            System.out.println();
	            System.out.println("\nEnter Examination Fee:");
	            clinic.patientsList.get(index).setDoctorFee(scan.nextDouble());
	            clinic.addVisit(clinic.appointmentsRecord.get(index));
	            System.out.println("\nDone!\n");        
	            break;

	        case 2:
	        	System.out.println(logOut);
	            scan.nextLine();
	            return;
	            
	        default:
	            System.out.println(invalid);
	            System.out.println();
	        }
		}	
	}
	
	public static void patientActivities() {
		while (true) {
			int i;
			int index;
			Patient patient = (Patient)users.get(userIndex);
			ArrayList<Integer> appIndex = new ArrayList<>();
			
			System.out.println("Patient Menu");
	        System.out.println(separate);
	        System.out.println("1. Add Appointment");
	        System.out.println("2. Edit Appointment");
	        System.out.println("3. Delete Appointment");
	        System.out.println("4. Show Appointment");
	        System.out.println("5. Sign Out");
	        System.out.println(selectOption);
	        int select = scan.nextInt();
	        System.out.println();
	        
	        switch (select) {
	        case 1:
	        	System.out.println("Choose Day:");
				String day = scan.next();
				System.out.println("Choose Month:");
				String month = scan.next();
				System.out.println("Choose Year:");
				String year = scan.next();
				System.out.println("Choose Time:");
				String time = scan.next();
				
				String date = day + "/" + month + "/" + year;

				boolean validAppointment = clinic.addAppointment(new Appointment(date, time), patient);
				if (validAppointment)
					System.out.println("Add Appointment Successfully.\n");
				else
					System.out.print(error);
				break;	
	        	
	        case 2:
	        	i = 1;
	        	appIndex.clear();
	        	System.out.println(appointmentList);
	            System.out.println(separate2);
	        	for (int j = 0; j < clinic.appointmentsRecord.size(); j++) {
	        		if (clinic.patientsList.get(j).equals(patient) && clinic.reservationType.get(j).equals(1)) {
	        			System.out.println(i++ + ". " + clinic.appointmentsRecord.get(j).date + timeString + clinic.appointmentsRecord.get(j).time);
	        			appIndex.add(j);
	        		}
	        	}
	        	
	        	System.out.println();
	            System.out.println("\nPlease select an appointment:");
	            index = scan.nextInt();
            	if (index > i || index < 1) {
            		System.out.println(invalid);
            		break;
            	}
	            index--;
	            index = appIndex.get(index);          
	            System.out.println();
	            System.out.println("Choose New Day:");
				String newDay = scan.next();
				System.out.println("Choose New Month:");
				String newMonth = scan.next();
				System.out.println("Choose New Year:");
				String newYear = scan.next();
				System.out.println("Choose New Time:");
				String newTime = scan.next();
				
				String newdate = newDay + "/" + newMonth + "/" + newYear;
				
				validAppointment = clinic.editAppointment(clinic.appointmentsRecord.get(index), new Appointment(newdate, newTime));

				if (validAppointment)
					System.out.println("Edit Appointment Successfully.\n");
				else
					System.out.print(error);
				
				break;
	        	
	        	
	        case 3:
	        	i = 1;
	        	appIndex.clear();
	        	System.out.println(appointmentList);
	            System.out.println(separate2);
	        	for (int j = 0; j < clinic.appointmentsRecord.size(); j++) {
	        		if (clinic.patientsList.get(j).equals(patient) && clinic.reservationType.get(j).equals(1)) {
	        			System.out.println(i++ + ". " + clinic.appointmentsRecord.get(j).date + timeString + clinic.appointmentsRecord.get(j).time);
	        			appIndex.add(j);
	        		}
	        	}
	        	
	        	System.out.println();
	            System.out.println("\nPlease select an appointment:");
	            index = scan.nextInt();
	            if (index > i || index < 1) {
            		System.out.println(invalid);
            		break;
            	}	
	            index--;
	            index = appIndex.get(index);          
	            System.out.println();
	            			
				validAppointment = clinic.deleteAppointment(clinic.appointmentsRecord.get(index));

				if (validAppointment)
					System.out.println("Delete Appointment Successfully.\n");
				else
					System.out.print(error);
				
				break;
	        	
	        case 4:
	        	i = 1;
	        	appIndex.clear();
	        	System.out.println(appointmentList);
	            System.out.println(separate2);
	        	for (int j = 0; j < clinic.appointmentsRecord.size(); j++) {
	        		if (clinic.patientsList.get(j).equals(patient) && clinic.reservationType.get(j).equals(1)) {
	        			System.out.println(i++ + ". " + clinic.appointmentsRecord.get(j).date +	timeString + clinic.appointmentsRecord.get(j).time);
	        			appIndex.add(j);
	        		}
	        	}
	        	
	        	System.out.println();
	        	break;
	        	
	        case 5:
	        	System.out.println(logOut);
	            scan.nextLine();
	            return;
	            
	        default:
	            System.out.println(invalid);
	            System.out.println();
	        }
		}
	}
	
	public static void secretaryActivities() {
		while (true) {
			int i = 1;
			int index;
			int numOfVisits = 0;
			Report report = new Report();
					
	        System.out.println("Secretary Menu");
	        System.out.println(separate);
	        System.out.println("1. Print Invoice");
	        System.out.println("2. Sign Out");
	        System.out.println(selectOption);
	        int select = scan.nextInt();
	        System.out.println();
	        
	        switch (select) {
	        case 1:
	        	 System.out.println("Patients List:");
		            System.out.println(separate2);
		            i = 1;
		            for (int j = 0; j < clinic.patientsList.size(); j++)
		            	System.out.println(i++ +". " + clinic.patientsList.get(j).name);
		            System.out.println();
		            System.out.println("\nPlease select a patient:");
		            index = scan.nextInt();
		            if (index > i || index < 1) {
	            		System.out.println(invalid);
	            		break;
	            	}		            	
		            index--;
		            System.out.println();
		            Patient patient = clinic.patientsList.get(index);
		            Invoice invoice = new Invoice(patient.getServices(), patient.getDoctorFee());
		            invoice.printInvoice();
		            patient.getServices().clear();
		            patient.setDoctorFee(0.00);
		            System.out.println();
		            break;
	        	
	        case 2:
	        	System.out.println(logOut);
	            scan.nextLine();
	            return;
	            
	        default:
	            System.out.println(invalid);
	            System.out.println();
	        		
	        }
		}
	}
}