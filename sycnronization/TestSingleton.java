
public class TestSingleton {
    public void ejecuta() {
        for(int i = 0; i < 100; i++) {
            new Thread(() -> Singleton.getInstance()).start();
        }
        
        System.out.println(Singleton.getInstaciasConcurrentes());
    }
    
    public static void main(String[] args) {
        new TestSingleton().ejecuta();
    }
}

