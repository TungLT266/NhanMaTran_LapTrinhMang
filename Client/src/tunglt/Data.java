package tunglt;

import java.io.Serializable;

/**
 *
 * @author Le Thanh Tung
 */
public class Data implements Serializable {
    private static final long serialVersionUID = 1L;
    public String type;
    public float[][] maTran1;
    public int m1;
    public int n1;
    public float[][] maTran2;
    public int m2;
    public int n2;
    
    public Data(String type, float[][] maTran1,int m1, int n1, float[][] maTran2, int m2, int n2){
        this.type = type;
        this.maTran1 = maTran1;
        this.m1 = m1;
        this.n1 = n1;
        this.maTran2 = maTran2;
        this.m2 = m2;
        this.n2 = n2;
    }
}
