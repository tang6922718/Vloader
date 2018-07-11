package util;



import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringClean {
    public Map TotalroomClean(String s){
        s = s.trim();
        Map map = new HashMap();
        String[] result = s.split("  ");
        for(String temp:result){
            if(temp.contains("开业")) map.put("opentime",temp);
            if(temp.contains("房")) map.put("totalroom",temp);
            if(temp.contains("装修")) map.put("decorate",temp);
        }
        return map;
    }

    public String TelClean(String s){
        String result = "未查找到联系方式";
        Pattern p = Pattern.compile("电话.*\\d{4,}");
        Matcher m = p.matcher(s);
        while(m.find()){
            result = m.group(0);
        }
        result = result.replaceAll("传真.*","").replaceAll("电话","").replaceAll("、","|");
        return result;
    }

    public String RoadClean(String s){
        s = s.replace("，","");
        s = s.replace("。","");
        return s;
    }

    public String IdClean(String s){
        Pattern p = Pattern.compile("\\d{4,}");
        Matcher m = p.matcher(s);
        String i = null;
        while(m.find()){
            i = m.group(0);
        }
        return i;
    }

    public String QunarIdClean(String s){
        Pattern p =Pattern.compile("dt-\\d{1,5}");
        Matcher m = p.matcher(s);
        String i =null;
        while(m.find()){
            i=m.group(0);
        }
        return i;
    }

    public String QunarDetailClean(String s,String target){
        String[] list = s.split(" ");
        for(String temp : list){
            if(temp.contains(target)) return temp;
        }
        return "";
    }
}
