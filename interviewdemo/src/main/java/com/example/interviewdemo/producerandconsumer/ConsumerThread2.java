package com.example.interviewdemo.producerandconsumer;

public class ConsumerThread2 extends Thread {

    private Resource2 resource;
    public ConsumerThread2(Resource2 resource, String name){
        this.setName(name);
            this.resource = resource;
        }

    @Override
    public void run() {
        while (true){

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
resource.remove();

        }


    }
}
