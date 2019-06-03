package com.example.interviewdemo.producerandconsumer;

public class ProducerThread2 extends Thread {

    private Resource2 resource;


    public ProducerThread2(Resource2 resource, String name){
        this.setName(name);
        this.resource=resource;
    }

    @Override
    public void run() {
        while (true){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            resource.add();

        }


    }
}
