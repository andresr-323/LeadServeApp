package com.example.leadserve;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TierEListDataPump{
    public static HashMap<String, List<String>> getData(tierOne t1, tierTwo t2, tierThree t3) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        ArrayList<String> tier1 = new ArrayList<String>();
        if(t1.getLEAD1000() == 1 && t1.getLEADVerf() == 2){
            tier1.add("Complete");
        }else{
            if(t1.getLEADVerf() == 1){
                tier1.add("Under Review");
            }else{
                tier1.add("Not Complete");
            }
        }

        if(t1.getCoachingProgram() == 1 && t1.getCoachingVerf() == 2){
            tier1.add("Complete");
        }else{
            if(t1.getCoachingVerf() == 1){
                tier1.add("Under Review");
            }else{
                tier1.add("Not Complete");
            }
        }

        if(t1.getShowcase() == 1 && t1.getShowVer() == 2){
            tier1.add("Complete");
        }else{
            if(t1.getShowVer() == 1){
                tier1.add("Under Review");
            }else{
                tier1.add("Not Complete");
            }
        }

        ArrayList<String> tier2 = new ArrayList<String>();
        if(t2.getLEAD2000() == 1 && t2.getLEADVerf() == 2){
            tier2.add("Complete");
        }else{
            if(t2.getLEADVerf() == 1){
                tier2.add("Under Review");
            }else{
                tier2.add("Not Complete");
            }
        }

        if(t2.getFivehour() == 1 && t2.getFiveVerf() == 2){
            tier2.add("Complete");
        }else{
            if(t2.getFiveVerf() == 1){
                tier2.add("Under Review");
            }else{
                tier2.add("Not Complete");
            }
        }

        if(t2.getLegacyProjectProp() == 1 && t2.getLegacyVerf() == 2){
            tier2.add("Complete");
        }else{
            if(t2.getLegacyVerf() == 1){
                tier2.add("Under Review");
            }else{
                tier2.add("Not Complete");
            }
        }

        if(t2.getShowcase() == 1 && t2.getShowcaseVerf() == 2){
            tier2.add("Complete");
        }else{
            if(t2.getShowcaseVerf() == 1){
                tier2.add("Under Review");
            }else{
                tier2.add("Not Complete");
            }
        }

        ArrayList<String> tier3 = new ArrayList<String>();
//        if(t3.getLEAD3000() == 1){        OG STUFF**********************************
//            tier3.add("Complete");
//        }else{
//            tier3.add("Not Complete");
//        }

        if(t3.getLEAD3000() == 1 && t3.getLEADVerf() == 2){
            tier3.add("Complete");
        }else{
            if(t3.getLEADVerf() == 1){
                tier3.add("Under Review");
            }else{
                tier3.add("Not Complete");
            }
        }

        if(t3.getLeadershipLegProj() == 1 && t3.getLeadershipLegVerf() == 2){
            tier3.add("Complete");
        }else{
            if(t3.getLeadershipLegVerf() == 1){
                tier3.add("Under Review");
            }else{
                tier3.add("Not Complete");
            }
        }

        if(t3.getLeadershipPort() == 1 && t3.getPortVerf() == 2){
            tier3.add("Complete");
        }else{
            if(t3.getPortVerf() == 1){
                tier3.add("Under Review");
            }else{
                tier3.add("Not Complete");
            }
        }

        if(t3.getShowcase() == 1 && t3.getShowcaseVerf() == 2){
            tier3.add("Complete");
        }else{
            if(t3.getShowcaseVerf() == 1){
                tier3.add("Under Review");
            }else{
                tier3.add("Not Complete");
            }
        }
//        expandableListDetail.put("Tier One Progress", tier1);
//        expandableListDetail.put("Tier Two Progress", tier2);
        expandableListDetail.put("Tier Three Progress", tier3);
        expandableListDetail.put("Tier Two Progress", tier2);
        expandableListDetail.put("Tier One Progress", tier1);

        return expandableListDetail;
    }
}
