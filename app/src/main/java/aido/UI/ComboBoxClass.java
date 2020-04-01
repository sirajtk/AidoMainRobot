/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package aido.UI;


import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.provider.SyncStateContract.Constants;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;


import aido.common.CommonlyUsed;

/**
 *
 * @author Sumeendranath
 */
public class ComboBoxClass extends Spinner {

	private ArrayAdapter<ImageTextComboItem>arrForSpinner;
   
	String nameOfThisCombo = "no-name";
	Context _maincontext;
	


	
	int _futureIndexBind = -100;
	boolean _entertainsNoneVal = false;
	String _fontname;
	boolean _isGettingFilled = false;
	
	boolean _masterFunctionIsGettingFilled = false;
	
	boolean _bindingIsHappening = false;
	
    public ComboBoxClass(Context context, AttributeSet attrs)
    {
    	super(context,attrs);
    	
    }

    public ComboBoxClass(Context mainContext) {
		// TODO Auto-generated constructor stub
    	super(mainContext);
    	_maincontext = mainContext;
    	_entertainsNoneVal = true;
    	setConfiguration();

	}
    
    public void setWhiteDesign()
	{
		GradientDrawable gd = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0xFFFFFFFF ,0xFFFFFFFF});
	    gd.setCornerRadius(30f);
	    
        //gd.setStroke(10, SetViewStyle.BLUECOLOR);
	    //this.setBackgroundDrawable(gd);
	    
	    BitmapDrawable bg = new BitmapDrawable(BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_menu_more));
	    bg.setGravity(Gravity.RIGHT | Gravity.BOTTOM);		

	    Drawable[] layers = new Drawable[2];
	    layers[0] = gd;
	    layers[1] = bg;

	    LayerDrawable layerDrawable = new LayerDrawable(layers);
	    //setImageDrawable(layerDrawable);
	    

	    setBackgroundDrawable(layerDrawable);
	    //setBackgroundResource(android.R.drawable.arrow_down_float);;

	}
    private void setConfiguration()
    {
    	GradientDrawable gd = new GradientDrawable(
	            GradientDrawable.Orientation.TOP_BOTTOM,
	            new int[] {0xFFD8D8D8,0xFF6666FF});
	    gd.setCornerRadius(3f);
	    
	    //this.setBackgroundDrawable(gd);
    }
    

    
    public ComboBoxClass(Context mainContext,  boolean entertainsNoneVal, String fontname) {
		// TODO Auto-generated constructor stub
    	super(mainContext);
    	_maincontext = mainContext;
    	_entertainsNoneVal = entertainsNoneVal;
    	_fontname = fontname;
    	setConfiguration();
	}
    
    public void setFont(String fontname)
	{
		/*if(!CommonlyUsed.stringIsNullOrEmpty(fontname))
		{
			Typeface font = Typeface.createFromAsset(_maincontext.getAssets(), fontname);  
			
			this.setTypeface(font,Typeface.BOLD); 
		}
		else
		{
			this.setTypeface(null,Typeface.BOLD); 
		}*/
	}
    
    public void appendItem(String stringToAdd)
    {
    	setIsGettingFilled(true);
    	
		if(CommonlyUsed.stringIsNullOrEmpty(stringToAdd))
		{
	    	setIsGettingFilled(false);
	    	return;
		}
		if(arrForSpinner != null)
		{
    		arrForSpinner.add(new ImageTextComboItem(stringToAdd));
		}
		else
		{
			clearAllItems(true);
    		arrForSpinner.add(new ImageTextComboItem(stringToAdd));
		}
    	
		bindIndexToFuture_internal();
		{
			setIsGettingFilled(false);
		}

    }
    
    public void appendImageTextItem(ImageTextComboItem imgText)
    {
    	setIsGettingFilled(true);
    	
		if(!imgText.isValid((ImageTextComboItem.TEXT)) && 
		   !imgText.isValid((ImageTextComboItem.IMAGE))
				)
		{
	    	setIsGettingFilled(false);
	    	return;
		}
		if(arrForSpinner != null)
		{
    		arrForSpinner.add(imgText);
		}
		else
		{
			clearAllItems(true);
    		arrForSpinner.add(imgText);
		}
    	
		bindIndexToFuture_internal();
		{
			setIsGettingFilled(false);
		}

    }
    
    
	public void additems(Vector<String> vectorListToAdd)
    {
    	setIsGettingFilled(true);

		if(vectorListToAdd == null || vectorListToAdd.size() <=0)
		{
	    	this.clearAllItems(true);
	    	setIsGettingFilled(false);
	    	return;
		}
    	this.clearAllItems(true);
    	//arrForSpinner = new ArrayAdapter<CharSequence>(this.getContext(), android.R.layout.simple_spinner_item);
    	
    	arrForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.setAdapter(arrForSpinner);
    	for(int i=0;i<vectorListToAdd.size();i++)
    	{
    		arrForSpinner.add(new ImageTextComboItem(vectorListToAdd.get(i)));
    	}
    	
    	bindIndexToFuture_internal();
    	setIsGettingFilled(false);
    }
	public void additems(List<String> vectorListToAdd)
    {
    	setIsGettingFilled(true);

		if(vectorListToAdd == null || vectorListToAdd.size() <=0)
		{
	    	this.clearAllItems(true);
	    	setIsGettingFilled(false);
	    	return;
		}
    	this.clearAllItems(true);
    	//arrForSpinner = new ArrayAdapter<CharSequence>(this.getContext(), android.R.layout.simple_spinner_item);
    	
    	arrForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.setAdapter(arrForSpinner);
    	for(int i=0;i<vectorListToAdd.size();i++)
    	{
    		arrForSpinner.add(new ImageTextComboItem(vectorListToAdd.get(i)));
    	}
    	
    	bindIndexToFuture_internal();
    	setIsGettingFilled(false);
    }
	
	public void additems(ListCustom vectorListToAdd)
    {
    	setIsGettingFilled(true);

		if(vectorListToAdd == null || vectorListToAdd.size() <=0)
		{
	    	this.clearAllItems(true);
	    	setIsGettingFilled(false);
	    	return;
		}
    	this.clearAllItems(true);
    	//arrForSpinner = new ArrayAdapter<CharSequence>(this.getContext(), android.R.layout.simple_spinner_item);
    	
    	arrForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.setAdapter(arrForSpinner);
    	for(int i=0;i<vectorListToAdd.size();i++)
    	{
    		arrForSpinner.add(new ImageTextComboItem(vectorListToAdd.getVectorElement(i)));
    	}
    	
    	bindIndexToFuture_internal();
    	setIsGettingFilled(false);
    }
	
	
	private ArrayAdapter<ImageTextComboItem> getNewSpinnerAdapter()
	{
		
        //CommonlyUsed.showMsg(_maincontext, "font name GV=" + _fontname);

		 return new ArrayAdapter<ImageTextComboItem>(this.getContext(),
                 android.R.layout.simple_spinner_item) {

      public View getView(int position, View convertView, android.view.ViewGroup Viewgrp) {
              View v = super.getView(position, convertView, Viewgrp);

              //CommonlyUsed.showMsg(_maincontext, "font name GV=" + _fontname);


              TextView tv = (TextView) v;
              
              ImageTextComboItem ITC = this.getItem(position);
              
              if(ITC.isValid(ImageTextComboItem.TEXT))
              {
            	  tv.setText(ITC.getItemString(ImageTextComboItem.TEXT));
              }
              else
              {
            	  tv.setText("");
              }
              
              if(ITC.isValid(ImageTextComboItem.IMAGE))
              {
            	  tv.setText("");
                 //tv.setBackgroundDrawable(ITC.getImageDrawable());
                 tv.setCompoundDrawablesWithIntrinsicBounds(ITC.getImageDrawable(),null, null, null);
              }
              else
              {
            	  tv.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

              }
              
              
              return v;
      }


      public View getDropDownView(int position,  View convertView,  android.view.ViewGroup parent) {
               View v =super.getDropDownView(position, convertView, parent);

              // CommonlyUsed.showMsg(_maincontext, "font name DD=" + _fontname);
              //v.setBackgroundColor(Color.GREEN);

              TextView tv = (TextView) v;
              
              ImageTextComboItem ITC = this.getItem(position);
              
              if(ITC.isValid(ImageTextComboItem.TEXT))
              {
            	  tv.setText(ITC.getItemString(ImageTextComboItem.TEXT));
              }
              else
              {
            	  tv.setText("");
              }
              
              if(ITC.isValid(ImageTextComboItem.IMAGE))
              {
                //tv.setBackgroundDrawable(ITC.getImageDrawable());
            	  tv.setText("");
            	  
            	  tv.setCompoundDrawablesWithIntrinsicBounds(ITC.getImageDrawable(),null, null, null);
            	  //tv.setCompoundDrawablePadding(0);
              }
              else
              {
            	  
            	  tv.setCompoundDrawablesWithIntrinsicBounds(null,null, null, null);

              }
              
              return v;
      }
};
	}

	
	private ArrayAdapter<BitmapDrawable> getNewImageAdapter()
	{
		

		 return new ArrayAdapter<BitmapDrawable>(this.getContext(),
                 android.R.layout.simple_spinner_item) {

      public View getView(int position, View convertView, android.view.ViewGroup Viewgrp) {
              View v = super.getView(position, convertView, Viewgrp);

              //CommonlyUsed.showMsg(_maincontext, "font name GV=" + _fontname);
              
              //((ImageView) v).setIm
              ((TextView) v).setText("");

              v.setBackgroundDrawable(this.getItem(position));
              
              return v;
      }


      public View getDropDownView(int position,  View convertView,  android.view.ViewGroup parent) {
               View v =super.getDropDownView(position, convertView, parent);

              // CommonlyUsed.showMsg(_maincontext, "font name DD=" + _fontname);
              //((TextView) v).setTypeface(externalFont);
              //v.setBackgroundColor(Color.GREEN);

               ((TextView) v).setText("");
               //v.setBackgroundDrawable(this.getItem(position));
               

              return v;
      }
};
	}

	

	public void additems(String[] stringlist)
    {
    	setIsGettingFilled(true);
		if(stringlist == null || stringlist.length <=0)
		{
	    	this.clearAllItems(true);
	    	setIsGettingFilled(false);
	    	return;
		}
    	this.clearAllItems(true);
    	//arrForSpinner = new ArrayAdapter<CharSequence>(this.getContext(), android.R.layout.simple_spinner_item);
    	arrForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.setAdapter(arrForSpinner);
    	for(int i=0;i<stringlist.length;i++)
    	{
    		arrForSpinner.add(new ImageTextComboItem(stringlist[i]));
    	}
    	
    	
    	bindIndexToFuture_internal();
    	setIsGettingFilled(false);
    	
    }
	
	
	public void addImages(String[] stringlist)
    {
    	setIsGettingFilled(true);
		if(stringlist == null || stringlist.length <=0)
		{
	    	this.clearAllItems(true);
	    	setIsGettingFilled(false);
	    	return;
		}
    	this.clearAllItems(true);
		ArrayAdapter<BitmapDrawable> img = getNewImageAdapter();

    	this.setAdapter(img);
    	//arrForSpinner = new ArrayAdapter<CharSequence>(this.getContext(), android.R.layout.simple_spinner_item);
    	arrForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	//this.setAdapter(arrForSpinner);
    	for(int i=0;i<stringlist.length;i++)
    	{
    		arrForSpinner.add(new ImageTextComboItem(stringlist[i]));
    		//img.add(CommonlyUsed.getDrawableFromImageFileName(projectSpecific.getRootDirectory() + "img.jpg"));
    		//img.add(tv);
    	}
    	
    	
    	bindIndexToFuture_internal();
    	setIsGettingFilled(false);
    	
    }

    public void clearAllItems(boolean masterGettingFilled) /// if clear is called internally, getting filled should not be unset
    {
    	if(!masterGettingFilled)
    	{
    		setIsGettingFilled(true);
    	}
    	//arrForSpinner.clear();
    	//this.setAdapter(null);
    	arrForSpinner = getNewSpinnerAdapter();
    	//arrForSpinner = new ArrayAdapter<CharSequence>(this.getContext(), android.R.layout.simple_spinner_item);
    	arrForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.setAdapter(arrForSpinner);
    	if(_entertainsNoneVal)
    	{
    		arrForSpinner.add(new ImageTextComboItem(""));
    	}
    	
    	if(!masterGettingFilled)
    	{
    		setIsGettingFilled(false);
    	}
    }
    
    
    /*void removeItem(int itemToRemove)
    {
    	setIsGettingFilled(true);
    	int position = arrForSpinner.getPosition(itemToRemove));
    	Object item = arrForSpinner.getItem(position);
    	arrForSpinner.remove((CharSequence) item);
    	setIsGettingFilled(false);
    }*/

    public String getSelectedText()
    {
       
    	try{
    ImageTextComboItem ITC = (ImageTextComboItem)this.getSelectedItem();
    
    //CommonlyUsed.showMsg(_maincontext, "Returing val- " + ITC.getItemString(ImageTextComboItem.TEXT));
    
    return ITC.getItemString(ImageTextComboItem.TEXT);
    	}
    	catch(Exception ex)
    	{
    	    //CommonlyUsed.showMsg(_maincontext, "Returing crashed");

    	}
    
       return "";
    }
    
    

	public void setName(String setHeirCombonamefromid) {
		// TODO Auto-generated method stub
		nameOfThisCombo = setHeirCombonamefromid;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return(nameOfThisCombo);
	}
	
	public void setSelectedIndex(int index) {
		// TODO Auto-generated method stub
		this.setSelection(index);
	}
	
	public int getSelectedIndex() {
		// TODO Auto-generated method stub
		return this.getSelectedItemPosition();
		
	}
	
	public void setText(String value)
	{
		//int index = this.fin
		//int searchindex = arrForSpinner.getPosition(value.substring(0, value.length() - 1));
		//int searchindex = arrForSpinner.getPosition(value);
		SpinnerAdapter arrAdapter = this.getAdapter();
		if(arrAdapter == null)
		{
			return;
		}
		for(int i=0;i<arrAdapter.getCount();i++)
		{
			CommonlyUsed.showMsg(_maincontext, "item -" );// arrForSpinner.getItem(i) );

		}
		
		

		//CommonlyUsed.showMsg(_maincontext, "found -" + searchindex );
		//if(searchindex >=0 )
		{
			//this.setSelectedIndex(searchindex);
		}
	}
	
	
	public void setFutureIndexBind(int index)
	{
    	setIsGettingFilled(true);
		_futureIndexBind = index;
		bindIndexToFuture_internal();
    	setIsGettingFilled(false);

	}
	
	public boolean hasFutureIndexBind() {
        return _futureIndexBind > 0;
    }
	
	private void bindIndexToFuture_internal()
	{

		_bindingIsHappening = true;
		if(_futureIndexBind < -1 ) { _bindingIsHappening = false;return;}
		if(this.getCount() > _futureIndexBind)
		{
			this.setSelectedIndex(_futureIndexBind);
		}
		else
		{
			this.setSelectedIndex(0);

		}
		
		_bindingIsHappening = false;

	}
	
	public boolean isBindingHappening()
	{
		return _bindingIsHappening;
	}
	
	
	public void setIsGettingFilled(boolean gettingfilled)
	{
		_isGettingFilled = gettingfilled;
	}
	
	public boolean isGettingFilled()
	{
		return _isGettingFilled;
	}
	
	
}
