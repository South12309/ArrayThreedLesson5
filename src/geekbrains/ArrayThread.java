package geekbrains;

public class ArrayThread {

    static final int size = 10000000;
    static final int h = size / 2;
    float[] arr = new float[size];

    float[] a1 = new float[h];
    float[] a2 = new float[h];

    public static void main(String[] args) {
        ArrayThread arrayThread = new ArrayThread();
        arrayThread.getTimeCalculation();
        arrayThread.getTimeCalculationWithThread();
    }

    public void getTimeCalculation() {
        fillArrayArr();
        long a = System.currentTimeMillis();
        calculate(arr, 0);
        System.out.println("Затраченное время при линейном исполнении " + (System.currentTimeMillis() - a));

    }

    public void getTimeCalculationWithThread() {

        fillArrayArr();

        long a = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread thread1 = new Thread(() -> a1 = calculate(a1, 0));
        Thread thread2 = new Thread(() -> a2 = calculate(a2, h));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Затраченное время при разделении по потокам " + (System.currentTimeMillis() - a));


    }

    private void fillArrayArr() {
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
    }

    private float[] calculate(float[] array, int startIndex) {
        for (int i = 0; i < array.length; i++) {

            array[i] = (float) (arr[startIndex] * Math.sin(0.2f + startIndex / 5) * Math.cos(0.2f + startIndex / 5) * Math.cos(0.4f + startIndex / 2));
            startIndex++;

        }
        return array;
    }
}
