package comp110;

import javafx.stage.Screen;

public class Runner extends Base {
	public static void main(String args[]){
		launch(args);
		System.out.println(Screen.getPrimary().getBounds().getHeight());
	}
}
