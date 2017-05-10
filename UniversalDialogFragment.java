import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mycompany.triage_v2.R;

import java.util.HashMap;
import java.util.Map;


public class UniversalDialogFragment extends DialogFragment {

    // String used to get the id of the Activity/Fragment that is calling the dialog
    private static final String PARENT_ID = "parent_id";

    // Set up a listener in the event that methods from activity/fragment need to be called
    private UniversalDialogFragmentListener listener;

    public interface UniversalDialogFragmentListener{
        // Methods to be called go here
    }

    /***********************************************************************************************
     * Creates a new instance of a dialog fragment
     *
     * @param parent_id     the R_id of the layout being used by the parent Activity/Fragment
     * @return              New instance of Dialog Fragment
     */
    public static UniversalDialogFragment newInstance(int parent_id){
        UniversalDialogFragment fragment = new UniversalDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARENT_ID, parent_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    // Required empty constructor
    public UniversalDialogFragment(){}

    // Android onCreate method
    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_universal, null);

        // Get the id of the parent Activity/Fragment
        int parent_id = getArguments().getInt(PARENT_ID);

        // New instance of DataStructures
        DataStructures dataStructures = new DataStructures();
        // Get the id of the text to be displayed based on parent_id
        int stringId = dataStructures.getString(parent_id);

        // Set TextView to desired text
        TextView textView = (TextView) view.findViewById(R.id.textview_universal);
        textView.setText(stringId);

        // Return Dialog
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    // Different onClick events can be set up here depending on parent_id
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        sendResult(Activity.RESULT_OK);
                    }
                }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    // Different onClick events can be set up here depending on parent_id
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                    }
                }).create();
    }

    // Android onAttach method
    // Used to attach listener if implemented
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    /***********************************************************************************************
     * Sends results to parent Activity/Fragment
     *
     * Current setup is for a Fragment and sending results to onActivityResult method
     *
     * @param resultCode        Result code used to identify user response
     */
    private void sendResult(int resultCode){
        if(getTargetFragment() == null){
            return;
        }

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, null);
    }

    /***********************************************************************************************
     * Creates a Map of parent ids and string ids
     * Each parent id corresponds to a parent Activity/Fragment
     * Each string id corresponds to the string needed to be displayed based on the parent
     * Strings are stored in the res/string folder
     */
    private class DataStructures{

        // Map withe key type Integer and Object type Integer
        private Map <Integer, Integer> dialogStrings;

        // Set up the Map with the values for parent ids and string ids
        @SuppressWarnings("unchecked")
        private DataStructures(){
            dialogStrings = new HashMap();
            
            // put all parent and string ids here as follows:
            // dialogStrings.put(R.id.parent_id, R.string.string_to_be_displayed_id); 
        }


        /*******************************************************************************************
         * Get the HashMap
         *
         * @return          HashMap of parent and string ids
         */
        private Map getDialogStrings(){
            return dialogStrings;
        }

        /*******************************************************************************************
         * Gets the id of the String associated with the id
         *
         * @param id        parent Activity/Fragment id
         * @return          String id of the String to be displayed
         */
        private int getString(int id){
            return dialogStrings.get(id);
        }


    }


}

