package comp110;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class ScrollPaneGenerator {
	
	public static ScrollPane makeScrollPane(Matchup m) {
		
		ScrollPane scrollpane = new ScrollPane();
		scrollpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollpane.setContent(Pane2Generator.Pane2(m));
		scrollpane.autosize();
		
		return scrollpane;
	}
}
