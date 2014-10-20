package com.epam.training.torpedo.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;


public class Client {

	@Autowired
	private Socket server;
	
	@Autowired
	private DataOutputStream ToServer;
	
	@Autowired
	private BufferedReader FromServer;

}
