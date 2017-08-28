package property;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xupeng5 on 2017/8/26.
 */
public class ShortPath {

    // 读json文件，获取id及各点之间的距离，并由此生成最短距离矩阵
    JSONObject jsonObject = JsonReader.jsonObject;
    JSONArray vertices = (JSONArray) jsonObject.get("vertices");
    JSONArray edges = (JSONArray) jsonObject.get("edges");
    int ncount = vertices.size();
    int max = 65535;
    int[] path = new int[ncount];
    Double[] cost = new Double[ncount];
    String rowId = "";
    String colId = "";

    Double[][] graph = new Double[ncount][ncount];


    /*
        * 求解各节点最短路径，获取path，和cost数组，
        * path[i]表示vi节点的前继节点索引，一直追溯到起点。
        * cost[i]表示vi节点的花费
        */
    private  void genShortestPathMatrix(Double[][] graph, int startIndex, int[] path, Double[] cost, int max) {
        int nodeCount = graph[0].length;
        Boolean[] v = new Boolean[nodeCount];
        //初始化 path，cost，V
        for (int i = 0; i < nodeCount; i++) {
            if (i == startIndex)//如果是出发点
            {
                v[i] = true;//
            } else {
                cost[i] = graph[startIndex][i];
                if (cost[i] < max) {
                    path[i] = startIndex;
                } else {
                    path[i] = -1;
                }
                v[i] = false;
            }
        }

        for (int i = 1; i < nodeCount; i++) //在nodeCount-1个节点中找出v最短距离的节点
        {
            Double minCost = Double.valueOf(max);
            int curNode = -1;
            for (int w = 0; w < nodeCount; w++) {
                if (!v[w]) //未在V集合中
                {
                    if (cost[w] < minCost) {
                        minCost = cost[w];
                        curNode = w;
                    }
                }
            }//for  获取最小权值的节点
            if (curNode == -1) {
                break;//剩下都是不可通行的节点，跳出循环
            }
            v[curNode] = true;
            for (int w = 0; w < nodeCount; w++) {
                if (!v[w] && (graph[curNode][w] + cost[curNode] < cost[w])) {
                    cost[w] = graph[curNode][w] + cost[curNode];//更新权值
                    path[w] = curNode;//更新路径
                }
            }//for 更新其他节点的权值（距离）和路径
        }//
    }

    // 由从外部输入的源点和终点id，获取这两点之间的最短距离
    public Double getTheOptimalWeight(String source, String to) {
        // nodeIdMap：从输入读来的id与矩阵中的节点编号对应起来的map
        Map<String, Integer> nodeIDMap = new HashMap<String, Integer>();
        for(int i=0; i<ncount; i++) {
            nodeIDMap.put(vertices.getJSONObject(i).get("_id").toString(),i);
        }
        // 矩阵初始化
        initMatrix(nodeIDMap);
        // graph设置成最短距离矩阵
        genShortestPathMatrix(graph,0,path,cost,max);

        int rowNo = nodeIDMap.get(source);
        int colNo = nodeIDMap.get(to);
        if(rowNo <0 || colNo <0) {
            return Double.valueOf(-1);
        }
        return graph[rowNo][colNo];
    }

    // 初始化矩阵
    private void initMatrix(Map<String, Integer> nodeIDMap) {
        // 1.全设置为0先
        for(int i=0; i<ncount; i++) {
            for(int j=0; j<ncount; j++) {
                graph[i][j] = Double.valueOf(max);
            }
        }
        // 2.将json文件中边 记录的权重加入进来
        // 从json中读取该边的源节点id和终节点id
        int row = -1;
        int col = -1;
        for(int k=0; k<edges.size(); k++) {
            rowId = edges.getJSONObject(k).get("_outV").toString();
            colId = edges.getJSONObject(k).get("_inV").toString();
            // 由edges里该边的源点和终点，获取在矩阵中对应的行号和列号
            row = nodeIDMap.get(rowId);
            col = nodeIDMap.get(colId);
            graph[row][col] = (Double)edges.getJSONObject(k).get("weight");
        }
    }
}



