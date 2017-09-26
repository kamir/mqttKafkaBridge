/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cloudera.iot.bridge.simple;

/**
 *
 * @author kamir
 */
public class CDDCBridge {

    public static void main(String[] args) {
        Thread cd = new Thread(new Runnable() {
            @Override
            public void run() {
                Cluster2DeviceLink cd = new Cluster2DeviceLink();
                cd.start();
            }
        });
        Thread dc = new Thread(new Runnable() {
            @Override
            public void run() {
                Device2ClusterLink cd = new Device2ClusterLink();
            }
        });
        
        cd.start();
        dc.start();

    }

}
