package com.magossi.defaultfirebase.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.magossi.defaultfirebase.model.BaseDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
public abstract class FirestoreRepository<T extends BaseDocument> {
	@Autowired
	private Firestore firestore;

	protected Class<T> classType;
	protected String collectionName;
	protected String className;

	FirestoreRepository(Class<T> classType, String collectionName){
		this.classType = classType;
		this.collectionName = collectionName;
		this.className = classType.getSimpleName().toLowerCase();
	}

	public List<T> findAll(){
		List<T> results = null;
		try{
			ApiFuture<QuerySnapshot> future = firestore.collection(collectionName).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			results = documents.stream().map(document -> document.toObject(classType)).collect(Collectors.toList());
		} catch (InterruptedException ie){
			log.info("Erro on load list of " + className);
		} catch (ExecutionException ee) {
			log.info("Erro on load list of " + className);
		}
		return results;
	}

	public T find(String id) {
		log.debug("find " + className + " id:" + id);
		T entity = null;
		try {
			ApiFuture<DocumentSnapshot> future = firestore.collection(collectionName).document(id).get();
			entity = verifyIfExists(future.get(), id);
		} catch (InterruptedException ie){
			log.info("Erro on find " + className + " with id: "+id);
		} catch (ExecutionException ee) {
			log.info("Erro on find " + className + " with id: "+id);
		}
		return entity;
	}

	public T save(T entity) {
		Assert.notNull(entity, "entity may not be null");
		try {
			if (entity.getId() == null) {
				generateHistorical(entity);
				ApiFuture<DocumentReference> addedDocRef = firestore.collection(collectionName).add(entity);
				DocumentReference documentReference = addedDocRef.get();
				String id = documentReference.getId();
				entity.setId(id);
				documentReference.update("id", id).get();
				log.info("Added document ["+collectionName+"] with ID: " + documentReference.getId());
			}
		} catch (InterruptedException ie){
			log.info("Erro on save list of " + className);
		} catch (ExecutionException ee) {
			log.info("Erro on save list of " + className);
		}
		return entity;
	}

	private T verifyIfExists(DocumentSnapshot document, String id){
		T entity = null;
		if (document.exists()) {
			entity = document.toObject(classType);
		} else {
			log.debug("No such document on collection: "+ collectionName +" with id: "+ id);
		}
		return entity;
	}

	private void generateHistorical(T entity){
		entity.setCreatedAt(LocalDateTime.now());
	}

	private Map<String, Object> removeNullValues(T userObject) {
		Gson gson = new GsonBuilder().create();
		Map<String, Object> map = new Gson().fromJson(
				gson.toJson(userObject), new TypeToken<HashMap<String, Object>>() {
				}.getType()
		);
		return map;
	}
}
