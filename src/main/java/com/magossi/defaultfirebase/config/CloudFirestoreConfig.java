package com.magossi.defaultfirebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class CloudFirestoreConfig {

	private final static String PROJECT_ID = "teste-java-c8da9";
	private final static String PATH = "/Users/rafaelmagossi/Git/default-firebase/teste-java-c8da9-firebase-adminsdk-cjmuu-7c784585e4.json";


	@Bean
	public Firestore firestore() throws IOException {
		FileInputStream serviceAccount = new FileInputStream(PATH);
		GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(credentials)
//				.setProjectId(PROJECT_ID)
//				.build();


		FirestoreOptions options =
				FirestoreOptions
						.newBuilder().setTimestampsInSnapshotsEnabled(true)
						.setCredentials(credentials)
						.setProjectId(PROJECT_ID)
						.build();
		return options.getService();


//		FirebaseApp.initializeApp(options);
//		return FirestoreClient.getFirestore();
	}
}
