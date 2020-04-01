package aido.Recipe;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitesuntech.aidohomerobot.R;

import org.w3c.dom.Text;

import aido.common.CommonlyUsed;
import aido.properties.ProjectProperties;
import aido.properties.StorageProperties;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDialog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDialog extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_RECIPENAME = "recipename";
    public static final String ARG_RECIPEDETAILS = "recipedetails";
    public static final String ARG_RECIPEIMAGE = "recipeimage";

    // TODO: Rename and change types of parameters
    private String _recipeName;
    private String _recipedetails;
    private String _recipeimage;


    TextView _tv_recipename;
    TextView _tv_recipedetails;
    ImageView _iv_recipeimage;

    Button _closebutton;

    private OnFragmentInteractionListener mListener;

    public RecipeDialog() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static RecipeDialog newInstance(String recipename, String recipedetails, String recipeimage) {
        RecipeDialog fragment = new RecipeDialog();
        Bundle args = new Bundle();
        args.putString(ARG_RECIPENAME, recipename);
        args.putString(ARG_RECIPEDETAILS, recipedetails);
        args.putString(ARG_RECIPEIMAGE, recipeimage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            _recipeName = getArguments().getString(ARG_RECIPENAME);
            _recipedetails = getArguments().getString(ARG_RECIPEDETAILS);
            _recipeimage = getArguments().getString(ARG_RECIPEIMAGE);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialogfragment_recipe, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
           // throw new RuntimeException(activity.toString()
           //         + " must implement OnFragmentInteractionListener");
        }

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _tv_recipename = getView().findViewById(R.id.tv_recipedisplay_name);
        _tv_recipedetails = getView().findViewById(R.id.tv_recipedisplay_recipe);
        _iv_recipeimage = getView().findViewById(R.id.iv_recipedisplay_image);


        _tv_recipename.setText(_recipeName);
        _tv_recipedetails.setText(_recipedetails);

        CommonlyUsed.putImageOnImageView(_iv_recipeimage, StorageProperties.getRecipeImage());

        _closebutton = getView().findViewById(R.id.bt_recipedisplay_close);
        _closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dismiss();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
