
//Overall System that runs everything and instantiates all the Managers
public class TheaterSystem {

	UserManager userManager = new UserManager();
	ReservationManager reservationManager = new ReservationManager();
	ShowManager showManager = new ShowManager();
	Prompter prompter = new Prompter();

	public static void main(String[] args) {
		TheaterSystem theater = new TheaterSystem();
		
		theater.showManager.initializeShows(); // Initializes the system (Creates all Shows)
		theater.prompter.loadSystem(); //Uses ReservationLoader to load previous data
		theater.prompter.initialPrompt(); // Begins prompting
	}
}
