package com.example.weatherapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.math.BigDecimal;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link frag_1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link frag_1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag_1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static View view;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static String give_my_json;
    static JSONObject json_my_json;

    private OnFragmentInteractionListener mListener;


    public frag_1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment frag_1.
     */
    // TODO: Rename and change types and number of parameters
    public static frag_1 newInstance(String my_json) {
        give_my_json = my_json;

        frag_1 fragment = new frag_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, my_json);
        Log.d("json in frag_1", my_json);


        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_frag_1, container, false);
        try {
            json_my_json = new JSONObject(give_my_json);
            Double str_windspeed = json_my_json.getJSONObject("currently").getDouble("windSpeed");
            //double str_precipitation = json_my_json.getJSONObject("currently").getDouble("precipProbability");
            BigDecimal bd_ws = new BigDecimal(str_windspeed);
            bd_ws = bd_ws.setScale(2,BigDecimal.ROUND_HALF_UP);
            TextView txt_windspeed = (TextView) view.findViewById(R.id.windspeed);
            txt_windspeed.setText(bd_ws+" mph");
            double str_pressure = json_my_json.getJSONObject("currently").getDouble("pressure");
            BigDecimal bd_press = new BigDecimal(str_pressure);
            bd_press = bd_press.setScale(2,BigDecimal.ROUND_HALF_UP);
            TextView txt_pressure = (TextView) view.findViewById(R.id.tab2_pressure);
            txt_pressure.setText(bd_press+" mb");
            double str_precipitation = json_my_json.getJSONObject("currently").getDouble("precipProbability");
            BigDecimal bd_prec = new BigDecimal(str_precipitation);
            bd_prec = bd_prec.setScale(2,BigDecimal.ROUND_HALF_UP);
            //String.format(".2f", str_precipitation);
            TextView txt_precipitation = (TextView) view.findViewById(R.id.tab2_precipitation);
            txt_precipitation.setText(bd_prec+" mmph");
            int str_temperature = json_my_json.getJSONObject("currently").getInt("temperature");
            TextView txt_temperature = (TextView) view.findViewById(R.id.tab2_temperature);
            txt_temperature.setText(str_temperature+"\u00B0"+"F");
            double str_humidity = json_my_json.getJSONObject("currently").getDouble("humidity");
            str_humidity=str_humidity*100;
//            str_humidity=(int)str_humidity;
//            String.format(".2f", str_humidity);
            BigDecimal bd1 = new BigDecimal(str_humidity);
            bd1 = bd1.setScale(2,BigDecimal.ROUND_HALF_UP);


            TextView txt_humidity = (TextView) view.findViewById(R.id.tab2_humidity);
            txt_humidity.setText(bd1+" %");
            double str_visibility = json_my_json.getJSONObject("currently").getDouble("visibility");
            BigDecimal bd2 = new BigDecimal(str_visibility);
            bd2 = bd2.setScale(2,BigDecimal.ROUND_HALF_UP);

            //String.format(".2f", str_visibility);
            TextView txt_visibility = (TextView) view.findViewById(R.id.tab2_visibility);
            txt_visibility.setText(bd2.toString()+" km");
            double str_cloudcover = json_my_json.getJSONObject("currently").getDouble("cloudCover");

            str_cloudcover = str_cloudcover*100;
            String tr_cloudcover = String.valueOf(str_cloudcover);

            TextView txt_cloudcover = (TextView) view.findViewById(R.id.tab2_cloudcover);
            txt_cloudcover.setText(tr_cloudcover + " %");
            double str_ozone = json_my_json.getJSONObject("currently").getDouble("ozone");
            BigDecimal bd_ozone = new BigDecimal(str_ozone);
            bd_ozone= bd_ozone.setScale(2,BigDecimal.ROUND_HALF_UP);
            TextView txt_ozone = (TextView) view.findViewById(R.id.tab2_ozone);
            txt_ozone.setText(bd_ozone + " DU");

            String str_icon = json_my_json.getJSONObject("currently").getString("icon");
            ImageView txt_icon = (ImageView) view.findViewById(R.id.tab2_icon);
            if( str_icon.equals("clear-night")){
                txt_icon.setImageResource(R.drawable.weathernight2);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);


            }else if(str_icon.equals("clear-day")){

                txt_icon.setImageResource(R.drawable.weathersunny);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);

            }
            else if(str_icon.equals("rain")){

                txt_icon.setImageResource(R.drawable.weatherrainy);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);



            }
            else if(str_icon.equals("snow")){

                txt_icon.setImageResource(R.drawable.weathersnowy);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);

            }
            else if(str_icon.equals("sleet")){

                txt_icon.setImageResource(R.drawable.weathersnowyrainy);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);

            }
            else if(str_icon.equals("fog")){

                txt_icon.setImageResource(R.drawable.weatherfog);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);

            }
            else if(str_icon.equals("wind")){

                txt_icon.setImageResource(R.drawable.weatherwindyvariant);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);

            }
            else if(str_icon.equals("cloudy")){

                txt_icon.setImageResource(R.drawable.weathercloudy);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);


            }
            else if(str_icon.equals("partly-cloudy-night")){

                txt_icon.setImageResource(R.drawable.weathernightpartlycloudy);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText("cloudy night");

            }
            else if(str_icon.equals("partly-cloudy-day")){

                txt_icon.setImageResource(R.drawable.weatherpartlycloudy);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText("cloudy day");

            }
            else{

                txt_icon.setImageResource(R.drawable.weathersunny);
                str_icon=str_icon.replace("-", " ");
                TextView textview_icon = (TextView) view.findViewById(R.id.tab2_txt_icon);
                textview_icon.setText(str_icon);

            }










        } catch (JSONException e) {
            e.printStackTrace();
        }



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
