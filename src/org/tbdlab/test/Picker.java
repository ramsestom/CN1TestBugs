package org.tbdlab.test;

import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundRectBorder;
import com.codename1.ui.plaf.Style;

public class Picker extends Container {

	protected Container contentPane;
	protected Component scrimPane;

	public Picker() {
		super(new LayeredLayout()); 
		contentPane = new Container(new BorderLayout());
		scrimPane = new Container();
		init();
	}

	private void init() 
	{
		this.setUIID("MDCModal");
		Style aStyle = getAllStyles();
		aStyle.setBgTransparency(0);
		aStyle.setMargin(0, 0, 0, 0);
		aStyle.setMarginUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		aStyle.setPadding(0, 0, 0, 0);
		aStyle.setPaddingUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});

		Style spStyle = scrimPane.getAllStyles();
		spStyle.setBgColor(0xFF0000);
		spStyle.setBgTransparency(255);
		spStyle.setMargin(0, 0, 0, 0);
		spStyle.setMarginUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		spStyle.setPadding(0, 0, 0, 0);
		spStyle.setPaddingUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		super.addComponent(scrimPane);

		Style cpStyle = contentPane.getAllStyles();
		cpStyle.setBgTransparency(0);
		cpStyle.setMargin(0, 0, 0, 0);
		cpStyle.setMarginUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		cpStyle.setPadding(0, 0, 0, 0);
		cpStyle.setPaddingUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		super.addComponent(contentPane);

		Style dStyle = contentPane.getAllStyles();
		dStyle.setBgColor(0xFFFFFF);
		dStyle.setBgTransparency(255);
		dStyle.setBorder(RoundRectBorder.create()
				.strokeColor(0)
				.strokeOpacity(255)
				.stroke(new Stroke(1, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1))
				.shadowSpread(0.5f)
				.shadowX(0f)
				.shadowY(0f)
				.shadowOpacity(180)
		);
		
		contentPane.setLayout(new BorderLayout());
				
		Label title_component = new Label("Select");
		Style mdStyle = title_component.getAllStyles();
		mdStyle.setFgColor(0x000000);
		mdStyle.setOpacity(222);
		mdStyle.setPadding(28, 8, 24, 24);
		mdStyle.setPaddingUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		mdStyle.setMargin(0,0,0,0);
		contentPane.addComponent(BorderLayout.NORTH, title_component);
		
		//Container body_component = new Container(new FlowLayout()); //new Container()
		Container body_component = new Container(new BorderLayout());
		body_component.setScrollable(false);
		Style bcStyle = body_component.getAllStyles();
		bcStyle.setBgTransparency(0);
		bcStyle.setPadding(24, 24, 24, 24);
		bcStyle.setPaddingUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		bcStyle.setMargin(0,0,0,0);
		
		contentPane.addComponent(BorderLayout.CENTER, body_component);
		
		
		Container picker = new Container(BoxLayout.y()); 
		picker.setScrollableY(true);
		for (int i =0; i< 25; i++) {
			Button b = new Button("Button "+i);
			picker.add(b);
			b.addActionListener(e -> System.out.println("You picked: " + b.getText()));
		}
		//body_component.addComponent(picker);
		body_component.addComponent(BorderLayout.CENTER, picker);
				
		contentPane.setGrabsPointerEvents(true); 
		scrimPane.addPointerPressedListener(new ActionListener<ActionEvent>() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});
	}


	public void show() {

		Form f = Display.getInstance().getCurrent();
		Component ref_parent = f;

		contentPane.layoutContainer();
		Dimension mdims = contentPane.getPreferredSize();
		int mwidth = 500;
		int mheight = Math.min(mdims.getHeight(), ref_parent.getHeight()-2*20);

		int vinset = ref_parent.getHeight() - mheight;
		int hinset = ref_parent.getWidth() - mwidth;
		int top = Math.max(0, vinset/2);
		int bottom = Math.max(0, vinset-top);
		int left = Math.max(0, hinset/2);
		int right = Math.max(0, hinset-left);

		show(top, bottom, left, right); 
	}


	private void show(int top, int bottom, int left, int right) 
	{
		Form f = Display.getInstance().getCurrent();
		Component ref_parent = f;

		createScrimPane(ref_parent); 
		
		int componentCount = this.getComponentCount();
		for(int iter = 0 ; iter < componentCount ; iter++) {
			Component childCmp = this.getComponentAt(iter);
			childCmp.getStyle().setOpacity(255, false);
		}

		LayeredLayout ml = (LayeredLayout) super.getLayout();   
		ml.setReferenceComponents(contentPane, ref_parent);
		ml.setInsets(contentPane, top+"px "+right+"px "+bottom+"px "+left+"px"); 

		LayeredLayout ll = (LayeredLayout) getLayeredPane(f).getLayout();
		ll.setInsets(scrimPane, "0px 0px 0px 0px"); 

		remove();

		getLayeredPane(f).addComponent(this);

		getLayeredPane(f).revalidate();
	}


	private void createScrimPane(Component bg_parent) 
	{
		Style spStyle = scrimPane.getAllStyles();
		spStyle.setMargin(0, 0, 0, 0);
		spStyle.setMarginUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});
		spStyle.setPadding(0, 0, 0, 0);
		spStyle.setPaddingUnit(new byte[] {Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS, Style.UNIT_TYPE_PIXELS});

		LayeredLayout ll = (LayeredLayout) super.getLayout();
		ll.setInsets(scrimPane, "0px 0px 0px 0px");

		scrimPane.setGrabsPointerEvents(true);
	}



	private void dispose() 
	{
		Form f = getComponentForm();
		Component ref_parent = f;

		if (f != null)
		{
			LayeredLayout ml = (LayeredLayout) super.getLayout();   
			ml.setReferenceComponents(contentPane, ref_parent);

			Container pp = getLayeredPane(f);
			revalidate();
			remove();
			pp.revalidate();
			Container c = getLayeredPane(f);
			if (c.getComponentCount() == 0) { 
				c.remove();
			}   
		}
		else {
			remove();
		}
	}


	private Container getLayeredPane(Form f) {
		Container c = (Container)f.getFormLayeredPane(Picker.class, true);
		if (!(c.getLayout() instanceof LayeredLayout)) {
			c.setLayout(new LayeredLayout());
		}
		return c;
	}

}
