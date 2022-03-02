package textbasedadventure.game;
public class Game{
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private CLS cls_var; 
    private int counter;
    private boolean gameRunning; 
    public Game(){
        parser = new Parser();
        player = new Player("Ryan"); 
        counter = 0; 
        gameRunning = true; 
    }

    public static void main(String []args){
        Game game = new Game();
        game.setUpGame();
        game.play();
    }

    public void printInformation(){
        System.out.println (currentRoom.getExitString()); 
        System.out.println (currentRoom.getShortDescription());
        System.out.println ("Health: " + player.getHealth());
        System.out.println (currentRoom.getInventoryString());
        System.out.println (player.getInventoryString());
    }
    
    public String introText(){
        String returnString = "You wake up standing in a house. This house has windows and a front door. \n You heard a voice from a hidden speaker saying, the only way you can leave is if you obtain all of the special items. Howver, there is a poisonous gas that eats away at your health every time you move. \n He ends by saying if you try to leave before grabbing all of the said items, you will die.";
        return returnString; 
    }
    
    public void printInventory(){
        System.out.println ("Health: " + player.getHealth());
        System.out.println (currentRoom.getInventoryString());
        System.out.println (player.getInventoryString());
    }
    public void setUpGame(){
        Room livingRoom = new Room ("living room", "You are in the living room" , "You are in the living room of the house with rooms surrounding you and a staircase leading down. On the floor is a backpack.");
        Room garage = new Room ("garage", "You are in the garage", "You are in the garage and you see a abnormally bright pink car and a flashlight. ");
        Room kitchen = new Room ("kitchen", "You are in the kitchen.", " You are in the kitchen. In the kitchen, there is a refrigerator, some pots and pans, and a comically large spoon that is suspiciously dirty.  ");
        Room bedroom = new Room ("bedroom", "You are in the bedroom", "You are in the bedroom, and in said bedroom, there are 2 mini shields and a big shield.");
        Room bathroom = new Room ("bathroom","You are in the bathroom", "You are in the bathroom and in the bathroom, there is a window, a toilet, and some basic bathroom essentials, like toothpaste, shaving cream, and an oddly large toothbrush");
        Room basement = new Room ("basement","You are in the basement.", "You are in the basement. In the basement, it is pitch black. However, you sense the prescene of someone else. You walk forward and run into a large cutout of a banana. ");
        
        Item backpack = new Item ("backpack", "A backpack with room for about 7-8 average sized objects, or maybe one refrigerator. ");
        Item car = new Item ("car", "This car is a bright pink Mini Cooper, with a Lightning McQueen Sun Shield covering the winshield."); 
        Item flashlight = new Item ("flashlight", "A tiny flashlight, that is blinding if flashed on an individual.");
        Item refrigerator = new Item ("refrigerator", "The refrigerator...its big. ");
        Item pot = new Item ("pot", "A pot with a long handle...maybe this may help later.");
        Item pan = new Item ("pan", "A pan, also with a long handle...this also may help later.");
        Item spoon = new Item("spoon", "A spoon, with odd stains on it. I think that is chunky chili on it. ");
        Item minishield1 = new Item ("mini shield1", "A minishield, drinking this will give you 25 health. ");
        Item minishield2 = new Item ("mini shield2", "Another minishield, drinking this will give you 25 health.");
        Item bigshield = new Item ("big shield", "A big shield, drinking this will give you 50 shield."); 
        Item toothpaste = new Item ("toothpaste", "A tube of minty toothpaste...you should take this");
        Item shavingcream = new Item ("shaving cream", "A can of blue shaving cream...dont throw this");
        Item toothbrush = new Item ("toothbrush", "A Lightning McQueen toothbrush that looks half eaten. "); 
        Item bananacutout = new Item ("banana cutout", "A cutout of a banana...hope this helps you"); 
         
        livingRoom.setExit ("garage", garage);
        livingRoom.setExit ("kitchen", kitchen);
        livingRoom.setExit ("bathroom", bathroom);
        livingRoom.setExit ("bedroom", bedroom);
        livingRoom.setExit ("basement", basement);
        livingRoom.setItem ("backpack", backpack);

        garage.setExit ("living room", livingRoom);
        garage.setItem ("car", car);
        garage.setItem ("flashlight", flashlight);

        kitchen.setExit ("living room", livingRoom);
        kitchen.setItem ("refrigerator", refrigerator);
        kitchen.setItem ("pot", pot);
        kitchen.setItem ("pan", pan);
        kitchen.setItem ("spoon", spoon);

        bathroom.setExit("living room", livingRoom);
        bathroom.setItem ("toothpaste", toothpaste);
        bathroom.setItem ("shaving cream", shavingcream);
        bathroom.setItem ("toothbrush", toothbrush);

        bedroom.setExit ("living room", livingRoom);
        bedroom.setItem ("mini shield1", minishield1);
        bedroom.setItem ("mini shield2", minishield2);
        bedroom.setItem ("big shield", bigshield);
        minishield1.setDamage (25); 
        minishield2.setDamage (25);
        bigshield.setDamage (50); 
        
        basement.setExit ("living room", livingRoom);
        basement.setItem("banana cutout", bananacutout);
        
        currentRoom = livingRoom;
        try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
        System.out.println (introText()); 
        printInformation(); 
        play(); 
    }

    public void play(){
    	if (gameRunning==false) {
        	System.out.println ("You lost, game over");
        	return;
        }
    	while(gameRunning) {            
            Command command = parser.getCommand(); 
            try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
            System.out.println ("Moves: "+ counter); 
        }
        
        
    }

    public void processCommand(Command command){
        String commandWord=command.getCommandWord().toLowerCase();
        switch (commandWord){
            case "speak":
                System.out.println ("you wanted me to speak the word, " + command.getSecondWord());
                break;  
            case "go":
                goRoom (command);
                break;
            case "grab":
                grab(command);
                break;
            case "drop":
                drop (command);
                break;
            case "inspect":
                inspect (command);
                break;
            case "drink": 
                drink (command); 
                break; 
            case "quit":
                System.out.println ("Quitting game...");
                gameRunning=false;
                break; 
            case "help":
            	help (command);
            	break; 
        }
        counter++; 
        if (counter < 10) {
        	player.adjustHealth(-50); 
        }
        else {
        	player.adjustHealth (-10);
        }
    }

    public void grab(Command command){
        String item = "";
        if (!command.hasSecondWord()){
            System.out.println ("grab what?");  
            return; 
        }
        if (!command.hasLine()){
            item = command.getSecondWord(); 
        }
        else if (command.hasLine()){
            item = command.getSecondWord() + command.getLine();
        }
        Item itemToGrab = currentRoom.removeItem(item);
        if (itemToGrab==null){
            System.out.println ("you cannot grab that"); 
            return; 
        }
        else {
            System.out.println ("You grabbed the " + item);  
            player.setItem(item, itemToGrab);  
            System.out.println (currentRoom.getExitString()); 
            printInventory(); 
        }
    }

    public void drop(Command command){
        String item = ""; 
        if (!command.hasSecondWord()){
            System.out.println ("drop what?");
            return;
        }
        if (!command.hasLine()){
            item = command.getSecondWord(); 
        }
        else if (command.hasLine()){
            item = command.getSecondWord() + command.getLine();
        }
        Item itemToDrop = player.removeItem(item);
        if (itemToDrop==null){
            System.out.println ("you cannot drop that"); 
            return; 
        }
        else {
            System.out.println ("You dropped the " + item); 
            currentRoom.setItem(item, itemToDrop);
            System.out.println (currentRoom.getExitString()); 
            printInventory(); 
        }
    }
    
    public void inspect(Command command){
        String printString= "Looking at ";
        String thingToInspect = null; 
        if (!command.hasSecondWord()){
            System.out.println ("inspect what?");
            return;
        }
        if (!command.hasLine()){
            thingToInspect = command.getSecondWord(); 
        }
        else if (command.hasLine()){
            thingToInspect = command.getSecondWord() + command.getLine();
        }
        
        if (thingToInspect.equals (currentRoom.getName())){
            printString += "the room: " + currentRoom.getName()+ "\n" + currentRoom.getLongDescription();
        }
        else if (currentRoom.getItem(thingToInspect) != null){
            printString+= "the item: " + currentRoom.getItem(thingToInspect).getName()+ "\n" + currentRoom.getItem(thingToInspect).getDescription();
        }
        else if (player.getItem(thingToInspect) != null){
            printString+= "the item: " + player.getItem(thingToInspect).getName()+"\n" + player.getItem(thingToInspect).getDescription();
        }
        else{
            printString+= "\nYou can't look at that";
        }
        System.out.println (printString); 
    }
    
    
    public void drink(Command command){
        String thingToDrink = ""; 
        if (!command.hasSecondWord()){
            System.out.println ("drink what?");
            return;
        }
        if (!command.hasLine()){
            thingToDrink= command.getSecondWord();
        }
        else if (command.hasLine()){
            thingToDrink = command.getSecondWord() + command.getLine();
        }
        Item drinkMe= player.getItem (thingToDrink);
        player.adjustHealth (drinkMe.getDamage());
        
        if (thingToDrink==null){
            System.out.println ("you cannot drink that"); 
            return; 
        }
        else {
            System.out.println ("You drank the " + thingToDrink); 
            player.removeItem(thingToDrink);
            System.out.println (currentRoom.getExitString()); 
            printInventory(); 
        }
    }

    public void goRoom(Command command){
        String direction = "";
        if (!command.hasSecondWord()){
            System.out.println ("go where?"); 
            return; 
        }
        if (!command.hasLine()){
            direction = command.getSecondWord(); 
        }
        else if (command.hasLine()){
            direction = command.getSecondWord() + command.getLine();
        }
        Room nextRoom = currentRoom.getExit(direction); 
        if (nextRoom==null){
            System.out.println ("you cannot go there"); 
            return; 
        }
        else {
            currentRoom=nextRoom; 
            printInformation();
        }
    }
    
    public void help(Command command){
        if (!command.hasSecondWord()){
        	System.out.println ("Here are the available commands: \n go: type in 'go' followed by the exit you would like to go to. \n grab: type in 'grab' followed by the item you would like to pick up. \n drop: type in 'drop' followed by the item you would like to remove from your inventory. \n inspect: type in 'inspect' followed by the item you'd like to learn more about. \n drink: type in 'drink' followed by the potion you'd like to drink (mini shield1, mini shield2, big shield). \n quit: type in 'quit' to end the game.");
        	//(command) {
        }
        	//case "go":
        		
        
    }
    
    public void winGame(){
        //health must be greater than 100
    	//must have certain items in inventory
    }
    
    public void loseGame(){
        //ask why game doesnt end when health=0
    	if (player.getHealth()<= 0) {
    		gameRunning=false; 
    		System.out.println ("Oh no, you died. Thats so unfortunate.");
        	
        	
        }
    }
}
