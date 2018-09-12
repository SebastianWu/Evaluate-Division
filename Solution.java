import javafx.util.Pair; 
class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        // Build the graph that store the equations
        HashMap<String, HashMap<String,Double>> Division_map = new HashMap();
        for(int i=0; i<equations.length; i++){
            if(Division_map.get(equations[i][0]) == null){
                HashMap mp = new HashMap<>();
                Division_map.put(equations[i][0], mp);
            }
            if(Division_map.get(equations[i][1]) == null){
                HashMap mp = new HashMap<>();
                Division_map.put(equations[i][1], mp);
            }
            Division_map.get(equations[i][0]).put(equations[i][1],values[i]);
            Division_map.get(equations[i][1]).put(equations[i][0],1/values[i]);
        }
        //System.out.println(Division_map);
        
        // Using dfs to get the evaluation
        double[] res = new double[queries.length];
        for(int i=0; i<queries.length; i++){
            double v = dfs(queries[i][0], queries[i][1], Division_map, 1.0, new HashSet<String>());
            res[i] = v;
            //System.out.println(v);
        }
        
        return res;
    }
                   
    private double dfs(String str, String end, HashMap<String, HashMap<String,Double>> Division_map, Double v, HashSet<String> Traversed){
        if(str.equals(end) && Division_map.get(str) != null){
            return 1.0;
        }
        if(Division_map.get(str) != null){
            Traversed.add(str);
            for(Map.Entry<String, Double> entry : Division_map.get(str).entrySet()){
                if(entry.getKey().equals(end)){
                    v = v * entry.getValue();
                    return v;
                }
            }
            for(Map.Entry<String, Double> entry : Division_map.get(str).entrySet()){
                if(!Traversed.contains(entry.getKey())){    // avoid traverse cyclic graph
                    double temp = dfs(entry.getKey(),end,Division_map,v*entry.getValue(), Traversed);
                    if(temp>0){
                        return temp;
                    }// if return -1 then keep traverse
                }
            }
        }
        return -1.0;
    }
}
