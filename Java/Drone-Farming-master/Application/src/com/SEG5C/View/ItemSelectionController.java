package com.SEG5C.View;

import com.SEG5C.MainApp;
import com.SEG5C.Model.*;

import java.lang.Math;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ItemSelectionController {

	// References to UI.
	@FXML private TreeView<Component> treeView;
	@FXML private ImageView droneImage;
	@FXML private Button addButton;
	@FXML private Button editButton;
	@FXML private Button scanFarmButton;
	@FXML private Button visitButton;
	
	// Reference to the main application.
    private MainApp mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ItemSelectionController()
    {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
    	// Create Root Farm Item container
    	ItemContainer root = new ItemContainer(0, "My Farm");
    	
    	TreeItem<Component> rootFarmItem = new TreeItem<Component>(root);
    	treeView.setRoot(rootFarmItem);
    	treeView.setShowRoot(false);
    	
    	// Create barn item container and add to tree view
    	ItemContainer barnItemContainer = new ItemContainer(1, "Barn");
    	TreeItem<Component> barnItem = new TreeItem<Component>(barnItemContainer);
    	
    	// Create cow and pig items and add to barn
    	Item pigItem = new Item("Pig");
    	pigItem.setXCoordinate(725);
    	pigItem.setYCoordinate(10);
    	
    	Item cowItem = new Item("Cow");
    	barnItem.getChildren().add(new TreeItem<Component>(pigItem));
    	barnItem.getChildren().add(new TreeItem<Component>(cowItem));
    	
    	// Add barnItem to tree view and to list
    	root.addItem(barnItemContainer);
    	rootFarmItem.getChildren().add(barnItem);
    }
    
    /**
     * Adds item container to farm.
     */
    public void addItemContainer() {
    	TreeItem selection = getSelectionFromTreeView();
    	
    	if (selection != null) {
    		// TODO: Get user input for info about new item
    		System.out.println("Added new item container to " + selection.getValue());
    		selection.setExpanded(true);
    		selection.getChildren().add(new TreeItem<String>("New Item Container"));
    	}
    	else {
    		// TODO: add a popup that says below
    		System.out.println("Please select an item container to add this container to.");
    	}
    }
    
    /**
     * Adds item to farm.
     */
    public void addItem() {
    	TreeItem selection = getSelectionFromTreeView();
    	
    	if (selection != null) {
    		// TODO: Get user input for info about new item
    		System.out.println("Added new item to " + selection.getValue());
    		selection.setExpanded(true);
    		selection.getChildren().add(new TreeItem<String>("New Item"));
    	}
    	else {
    		// TODO: add a popup that says below
    		System.out.println("Please select an item container to add this item to.");
    	}
    }
    
    /**
     * Gets selected tree item from tree view.
     */
    private TreeItem getSelectionFromTreeView() {
    	TreeItem<Component> selectedItem = treeView.getSelectionModel().getSelectedItem();
    	return selectedItem == null ? null : selectedItem;
    }
   
    @FXML
    public void EditButton() {
    	
    	
    	ContextMenu contextMenu = new ContextMenu();
    	
    	// create menu items
        MenuItem menuItem1 = new MenuItem("Delete item");
        MenuItem menuItem2 = new MenuItem("Change name");
        MenuItem menuItem3 = new MenuItem("Change price");
        MenuItem menuItem4 = new MenuItem("Change location-x");
        MenuItem menuItem5 = new MenuItem("Change location-y");
        MenuItem menuItem6 = new MenuItem("Change length");
        MenuItem menuItem7 = new MenuItem("Change width");
        MenuItem menuItem8 = new MenuItem("Change height");

        contextMenu.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4,menuItem5,menuItem6,menuItem7,menuItem8);
        
        editButton.setContextMenu(contextMenu);
        editButton.setOnContextMenuRequested(e -> 
        contextMenu.show(editButton, e.getScreenX(), e.getScreenY()));
        
        
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Delete item Selected");
        	}
        });
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change name Selected");
        	}
        });
        menuItem3.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change price Selected");
        	}
        });
        menuItem4.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change location-x Selected");
        	}
        });
        menuItem5.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change location-y Selected");
        	}
        });
        menuItem6.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change length Selected");
        	}
        });
        menuItem7.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change width Selected");
        	}
        });
        menuItem8.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Change height Selected");
        	}
        });

    }
    
    @FXML
    public void AddButton() {
    	
    	
    	ContextMenu contextMenu = new ContextMenu();
    	
    	// create menu items
        MenuItem menuItem1 = new MenuItem("Add item");
        MenuItem menuItem2 = new MenuItem("Add item container");

        contextMenu.getItems().addAll(menuItem1,menuItem2);
        
        addButton.setContextMenu(contextMenu);
        addButton.setOnContextMenuRequested(e -> 
        contextMenu.show(addButton, e.getScreenX(), e.getScreenY()));
        
        
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Add item Selected");
        	}
        });
        menuItem2.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) { 
        	//TODO: Do actions rather than print message on console
        	System.out.println("Add item container Selected");
        	}
        });

    }
    
    /**
     * Makes drone visit selected item/item container.
     */
    @FXML
    public void visitClicked() {
    	
    	TreeItem selection = getSelectionFromTreeView();
    	
    	if (selection != null) {
    		// TODO: Move drone to selection.x and selection.y
    		Component component = (Component) selection.getValue();
    		visitCoordinateAndReturnToHome(component.getXCoordinate(), component.getYCoordinate());
    	}
    	else {
    		// TODO: add a popup that says below
    		System.out.println("Please select an item/item container to fly to.");
    	}
    }
    
    /**
     * Animates drone to move towards coordinates and return home.
     */
    private void visitCoordinateAndReturnToHome(double x, double y) {
    	// Turn off buttons while visiting
    	visitButton.setDisable(true);
    	scanFarmButton.setDisable(true);
    	
    	// Get rotation angle towards coordinate
    	double angle = Math.toDegrees(Math.atan(y/x));
    	
    	// Rotate towards coordinates
    	RotateTransition rotateForward = new RotateTransition(Duration.seconds(Math.toRadians(angle)/4));
    	rotateForward.setByAngle(angle);
    	
    	// Move to coordinates
    	TranslateTransition moveToCoordinates = new TranslateTransition();
    	moveToCoordinates.setDuration(Duration.seconds((x+y)/200));
    	moveToCoordinates.setToX(x);
    	moveToCoordinates.setToY(y);
    	
    	// Rotate back towards home position
    	RotateTransition rotateBackward = new RotateTransition(Duration.seconds(Math.toRadians(180)/4));
    	rotateBackward.setByAngle(180);
    	
    	// Move to home
    	TranslateTransition moveToHome = new TranslateTransition();
    	moveToHome.setDuration(Duration.seconds((x+y)/200));
    	moveToHome.setToX(0);
    	moveToHome.setToY(0);
    	
    	// Rotate to normal square
    	RotateTransition rotateNormal = new RotateTransition(Duration.seconds(Math.toRadians(90-angle)/4));
    	rotateNormal.setByAngle(90 - angle);

        SequentialTransition animation = new SequentialTransition(rotateForward, moveToCoordinates, rotateBackward, moveToHome, rotateNormal);
        
        animation.setOnFinished(finish -> {
	     	// Turn on buttons after visiting
	     	visitButton.setDisable(false);
	     	scanFarmButton.setDisable(false);
        });
        
        animation.setNode(droneImage);
        animation.play();
    }
    
    /**
     * Makes drone scan farm.
     */
    @FXML
    public void scanFarmClicked() {
    	// Turn off buttons while scanning
    	visitButton.setDisable(true);
    	scanFarmButton.setDisable(true);
    	
    	// Animate scanning
    	SequentialTransition animation = new SequentialTransition(
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(0, 525, 1.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(100, 525, 0.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(100, 0, 1.5),
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(200, 0, 0.5),
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(200, 525, 1.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(300, 525, 0.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(300, 0, 1.5),
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(400, 0, 0.5),
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(400, 525, 1.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(500, 525, 0.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(500, 0, 1.5),
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(600, 0, 0.5),
    	    rotateDrone(90, 0.5),
    	    visitCoordinate(600, 525, 1.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(725, 525, 0.5),
    	    rotateDrone(-90, 0.5),
    	    visitCoordinate(725, 0, 1.5),
    	    rotateDrone(-90, 0.5),
    	    
    	    // Go to (0,0)
    	    visitCoordinate(0,0, 3),
    	    rotateDrone(90, 0.5)
    	    
    	    // TODO: Go to drone home
    	);
    	
    	animation.setOnFinished(finish -> {
	     	// Turn on buttons after scanning
	     	visitButton.setDisable(false);
	     	scanFarmButton.setDisable(false);
        });
    	
        animation.setNode(droneImage);
        animation.play();
    }
    
    /**
     * Returns animation to move drone towards coordinates.
     */
    private TranslateTransition visitCoordinate(double x, double y, double duration) {
    	TranslateTransition visit = new TranslateTransition();
    	visit.setDuration(Duration.seconds(duration));
    	visit.setToX(x);
    	visit.setToY(y);
    	
    	return visit;
    }
    
    /**
     * Returns animation to rotate drone.
     */
    private RotateTransition rotateDrone(double degrees, double duration) {
    	RotateTransition rotate = new RotateTransition(Duration.seconds(duration));
    	rotate.setByAngle(degrees);
    	
    	return rotate;
    }
    
    /**
     * Called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }
    
}
