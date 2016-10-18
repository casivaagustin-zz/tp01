import java.util.*;

//http://www.spoj.com/problems/BUGLIFE/
public class Main {

    public static int marked[];

    public static boolean isTree(Vector<Vector<Integer>> v, int position) throws Exception {    
        Vector<Integer> sv = v.get(position);

        for (int i = 0; i < sv.size(); i++) {
            int next_position = sv.get(i);
            int csex = marked[position];

            if (marked[next_position] == csex) { //Si son del mismo sexo, corto
                throw new Exception();
            }

            if (marked[next_position] == 0) {
                if (csex == 1) {
                    csex = 2;
                } else {
                    csex = 1;
                }
                marked[next_position] = csex;
                int to_remove = v.get(next_position).indexOf(position);
                v.get(next_position).remove(to_remove);
                isTree(v, next_position);
            }
        } 
        return true;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int scenarios = s.nextInt();

        for (int cs = 0; cs < scenarios; cs++) {
            int bugs = s.nextInt();
            int interactions = s.nextInt();

            Vector<Vector<Integer>> vnodes = new Vector<Vector<Integer>>(1);

            for (int i = 0; i < bugs; i++) {
                vnodes.addElement(new Vector<Integer>(1));
            }

            for (int i = 0; i < interactions; i++) {
                int so = s.nextInt();
                int de = s.nextInt();
                vnodes.get(so - 1).addElement(de - 1);
                vnodes.get(de - 1).addElement(so - 1);
            }

            marked = new int[bugs];
            boolean result = true;

            System.out.println("Scenario #" + (cs + 1 ) + ":");

            for (int c = 0; c < bugs; c++) {
                if (marked[c] == 0) {                    
                    marked[c] = 1; //Empezamos con Sexo 1
                    if (vnodes.get(c).size() > 0) {                    
                        try {
                            result = isTree(vnodes, c);
                        } catch(Exception e) {
                            result = false;
                            break;
                        }
                    }
                }
            }

            if (result) {
                System.out.println("No suspicious bugs found!");
            } else {        
                System.out.println("Suspicious bugs found!");
            }
        }
    }
}
