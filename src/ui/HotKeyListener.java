package ui;

import javax.swing.JFrame;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;

@SuppressWarnings("serial")
public class HotKeyListener extends JFrame implements HotkeyListener, IntellitypeListener {

	public HotKeyListener() {
	
		if (JIntellitype.checkInstanceAlreadyRunning("TBA")) {
		   //logger.error("An instance of this application is already running");
			System.exit(1);
		}
	
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_WIN, (int)'T');
		JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT + JIntellitype.MOD_SHIFT, (int)'B');
		JIntellitype.getInstance().registerHotKey(3, JIntellitype.MOD_WIN, (int)'t');
		JIntellitype.getInstance().registerHotKey(4, JIntellitype.MOD_CONTROL, (int)'t');
		
		JIntellitype.getInstance().addHotKeyListener(this);
	
	
	}

	@Override
	public void onIntellitype(int aIdentifier) {
		// TODO Auto-generated method stub
		if (aIdentifier == 1) {
	        System.out.println("WINDOWS+A hotkey pressed");
	    } else if (aIdentifier == 2) {
	    	System.out.println("WINDOWS+A hotkey pressed");
	    } else if (aIdentifier == 3) {
	    	System.out.println("WINDOWS+A hotkey pressed");
	    } else if (aIdentifier == 4) {
	    	System.out.println("WINDOWS+A hotkey pressed");
	    }
		
	}

	@Override
	public void onHotKey(int aIdentifier) {
		// TODO Auto-generated method stub
		if (aIdentifier == 1) {
	        System.out.println("WINDOWS+A hotkey pressed");
	    } else if (aIdentifier == 2) {
	    	System.out.println("WINDOWS+A hotkey pressed");
	    } else if (aIdentifier == 3) {
	    	System.out.println("WINDOWS+A hotkey pressed");
	    } else if (aIdentifier == 4) {
	    	System.out.println("WINDOWS+A hotkey pressed");
	    }
	}
}