/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aido.UI;

import android.content.Context;
import android.text.Html;
import android.widget.TextView;

/**
 *
 * @author Sumeendranath
 */
public class ListItemForFilterCustom extends TextView{

    String _textval = "";
    String _secondtext = "";
    String _associatedText = "";
    
    Context _mainContext;
    
    boolean _selected = false;

    public ListItemForFilterCustom(Context context,String text, String text2) {
    	super(context);

        setText(text);
    	_mainContext = context;
        _textval = text;
        _secondtext = text2;
        
        createLayout();
        
    }
    
    //// important this is the one used for rendering...
    @Override
    public String toString() {
        return _textval;
    }

    public ListItemForFilterCustom(Context context, String text,String text2, String associatedText) {
    	super(context);

        setText(Html.fromHtml(text));
    	_mainContext = context;
    	
        _textval = text;
        _secondtext = text2;
        _associatedText = associatedText;
        createLayout();

    }

    
    
    public void createLayout()
    {
    	
    }
    public void setText(String text) {
        _textval = text;
    }
    public void setText2(String text2) {
        _secondtext = text2;
    }
    public void setAssociatedText(String associatedText) {
    	_associatedText = associatedText;
    }

    public String getText() {
        return _textval;
    }
    public String getText2() {
        return _secondtext;
    }
    public String getAssociatedText() {
        return _associatedText;
    }

    public void setSelected(boolean selection) {
        _selected = selection;
    }
    public boolean isSelected() {
        return _selected;
    }
     static public ListCustom sort(ListCustom e) {
        ListCustom v = new ListCustom();
        for (int count = 0; count < e.size(); count++) {
            ListItemForFilterCustom s = (ListItemForFilterCustom) e.get(count);
            int i = 0;
            for (i = 0; i < v.size(); i++) {
                ListItemForFilterCustom vitem = (ListItemForFilterCustom) v.get(i);
                int c = s.getText().compareTo(vitem.getText());
                if (c < 0) {
                    v.insertElementAt(s, i);
                    break;
                } else if (c == 0) {
                    break;
                }
            }
            if (i >= v.size()) {
                v.add(s);
            }
        }
        return v;
    }


}
