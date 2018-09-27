package org.tbdlab.test;

import com.codename1.components.InteractionDialog;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;


public class Overlay extends Container {

	
	public Overlay() {
		
		Button iDialogButton = new Button("Open interactionDialog");
        iDialogButton.addActionListener((e) -> {
        	InteractionDialog dlg = new InteractionDialog("Overlay Hello");
			dlg.setLayout(new BorderLayout());
			dlg.add(BorderLayout.CENTER, new Label("Hello Dialog from Overlay"));
			Button close = new Button("Close");
			close.addActionListener((ee) -> dlg.dispose());
			dlg.addComponent(BorderLayout.SOUTH, close);
			dlg.setFormMode(true);
			dlg.setAnimateShow(true);
			dlg.showPopupDialog(iDialogButton);
        });
        
        //Set some colored background to see it
        Container bgc = new Container(BoxLayout.y());
        bgc.getAllStyles().setBgColor(0xFF0000);
        bgc.getAllStyles().setBgTransparency(120);
        bgc.getAllStyles().setPadding(TOP, 50);
        bgc.getAllStyles().setPadding(BOTTOM, 50);
        bgc.add(new Label("Overlay"));
        bgc.addComponent(iDialogButton);
        
        this.setLayout(new BorderLayout());
        this.addComponent(BorderLayout.WEST, bgc);
    }
	
	
}
