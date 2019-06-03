package com.example.interviewdemo.producerandconsumer;

public class ConsumerThread extends Thread {

    private Resource resource;
    public ConsumerThread(Resource resource,String name){
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
