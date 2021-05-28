package com.example.parks.data;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.parks.controller.AppController;
import com.example.parks.model.Activities;
import com.example.parks.model.EntranceFees;
import com.example.parks.model.Images;
import com.example.parks.model.OperatingHours;
import com.example.parks.model.Park;
import com.example.parks.model.StandardHours;
import com.example.parks.model.Topics;
import com.example.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();
    public static void getParks(final AsyncResponse callback,String stateCode)
    {
        String url = Util.getParksUrl(stateCode);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Park park = new Park();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            park.setId(jsonObject.getString("id"));
                            park.setFullName(jsonObject.getString("fullName"));
                            park.setLatitude(jsonObject.getString("latitude"));
                            park.setLongitude(jsonObject.getString("longitude"));
                            park.setParkCode(jsonObject.getString("parkCode"));
                            park.setStates(jsonObject.getString("states"));

                            JSONArray imageList = jsonObject.getJSONArray("images");
                            List<Images> list = new ArrayList<>();
                            for (int j = 0; j <imageList.length() ; j++) {
                                Images images = new Images();
                                images.setCredit(imageList.getJSONObject(j).getString("credit"));
                                images.setTitle(imageList.getJSONObject(j).getString("title"));
                                images.setUrl(imageList.getJSONObject(j).getString("url"));

                                list.add(images);
                            }

                            park.setImages(list);

                            park.setWeatherInfo(jsonObject.getString("weatherInfo"));

                            park.setName(jsonObject.getString("name"));

                            park.setDesignation(jsonObject.getString("designation"));

                            // Setup Activities
                            JSONArray activityArray = jsonObject.getJSONArray("activities");
                            List<Activities> activitiesList = new ArrayList<>();
                            for (int j = 0; j <activityArray.length() ; j++) {
                                Activities activities = new Activities();
                                activities.setId(activityArray.getJSONObject(j).getString("id"));
                                activities.setName(activityArray.getJSONObject(j).getString("name"));

                                activitiesList.add(activities);
                            }
                            park.setActivities(activitiesList);

                            // Setup the Topics
                            JSONArray topicsArray = jsonObject.getJSONArray("topics");
                            List<Topics> topicsList = new ArrayList<>();
                            for (int j = 0; j <topicsArray.length() ; j++) {
                                Topics topics = new Topics();
                                topics.setId(topicsArray.getJSONObject(j).getString("id"));
                                topics.setName(topicsArray.getJSONObject(j).getString("name"));

                                topicsList.add(topics);
                            }
                            park.setTopics(topicsList);

                            // Operating Hours
                            JSONArray opHours = jsonObject.getJSONArray("operatingHours");
                            List<OperatingHours> operatingHours = new ArrayList<>();
                            for (int j = 0; j <opHours.length() ; j++) {
                                OperatingHours op = new OperatingHours();
                                op.setDescription(opHours.getJSONObject(j).getString("description"));

                                StandardHours standardHours = new StandardHours();
                                JSONObject hours = opHours.getJSONObject(j).getJSONObject("standardHours");

                                standardHours.setMonday(hours.getString("monday"));
                                standardHours.setTuesday(hours.getString("tuesday"));
                                standardHours.setWednesday(hours.getString("wednesday"));
                                standardHours.setThursday(hours.getString("thursday"));
                                standardHours.setFriday(hours.getString("friday"));
                                standardHours.setSaturday(hours.getString("saturday"));
                                standardHours.setSunday(hours.getString("sunday"));

                                op.setStandardHours(standardHours);

                                operatingHours.add(op);
                            }
                            park.setOperatingHours(operatingHours);

                            park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                            park.setDescription(jsonObject.getString("description"));
                            // Entrance Fees

                            JSONArray entranceFeesArray = jsonObject.getJSONArray("entranceFees");
                            List<EntranceFees> entranceFeesList = new ArrayList<>();
                            for (int j = 0; j <entranceFeesArray.length() ; j++) {
                               EntranceFees entranceFees =  new EntranceFees();
                               entranceFees.setCost(entranceFeesArray.getJSONObject(j).getString("cost"));
                               entranceFees.setDescription(entranceFeesArray.getJSONObject(j).getString("description"));
                               entranceFees.setTitle(entranceFeesArray.getJSONObject(j).getString("title"));

                               entranceFeesList.add(entranceFees);
                            }
                            park.setEntranceFees(entranceFeesList);

                            park.setWeatherInfo(jsonObject.getString("weatherInfo"));

                            parkList.add(park);
                        }

                        if (null != callback){ callback.processPark(parkList);}

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> error.printStackTrace());
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
