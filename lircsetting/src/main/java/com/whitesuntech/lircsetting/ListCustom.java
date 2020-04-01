/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.whitesuntech.lircsetting;

import java.util.Vector;

/**
 *
 * @author Sumeendranath
 */
public class ListCustom extends Vector<Object>{

    public ListCustom() {

        super();
    }
    
    public String getVectorElement(int elem)
    {
        if(this.size() - 1 < elem)
        {
            return "";
        }
        else
        {
            return (String) this.elementAt(elem);
        }
    }

    public boolean add(Object obj)
    {
        this.addElement(obj);
        return true;
    }
    public Object get(int index)
    {
        return this.elementAt(index);
    }

    static public ListCustom sort(ListCustom e) {
        ListCustom v = new ListCustom();
        for (int count = 0; count < e.size(); count++) {
            String s = (String) e.get(count);
            int i = 0;
            for (i = 0; i < v.size(); i++) {
                int c = s.compareTo((String) v.get(i));
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


