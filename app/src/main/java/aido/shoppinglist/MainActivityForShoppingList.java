package aido.shoppinglist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.whitesuntech.aidohomerobot.R;


public class MainActivityForShoppingList extends FragmentActivity {


	public static String BUNDLE_FROM_AIDO_ITEMNAME = "bundlefromaido_itemname";
	public static String BUNDLE_FROM_AIDO_QUANTITY = "bundlefromaido_quantity";
	public static String BUNDLE_FROM_AIDO_UNIT = "bundlefromaido_unit";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_container);
		
		FragmentManager fm = getSupportFragmentManager();

		ShoppingListFragment SLF = new ShoppingListFragment();

		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_FROM_AIDO_ITEMNAME, getIntent().getStringExtra(BUNDLE_FROM_AIDO_ITEMNAME));
		bundle.putString(BUNDLE_FROM_AIDO_QUANTITY, getIntent().getStringExtra(BUNDLE_FROM_AIDO_QUANTITY));
		bundle.putString(BUNDLE_FROM_AIDO_UNIT, getIntent().getStringExtra(BUNDLE_FROM_AIDO_UNIT));



		SLF.setArguments(bundle);


		Fragment lowerFragment = fm.findFragmentById(R.id.bottom_frame);
		if (lowerFragment == null) {
			fm.beginTransaction()
					.add(R.id.bottom_frame, SLF)
					.commit();
		}
		
//		Fragment upperFragment = fm.findFragmentById(R.id.top_frame);
//		if(upperFragment == null){
//			fm.beginTransaction()
//				.add(R.id.top_frame, new AddItemFragment())
//				.commit();
//		}

	}

	

}
