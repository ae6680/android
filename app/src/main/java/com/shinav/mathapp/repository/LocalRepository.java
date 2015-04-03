package com.shinav.mathapp.repository;

//public class LocalRepository {
//
//    private final Realm realm;
//
//    @Inject
//    public LocalRepository(Realm realm) {
//        this.realm = realm;
//    }
//
//    public List<Question> getQuestions() {
//        return realm.where(Question.class).findAll();
//    }
//
//    public Question getQuestion(String firebaseKey) {
//        return realm.where(Question.class).contains("firebaseKey", firebaseKey).findFirst();
//    }
//
//    public Story getStory(String firebaseKey) {
//        return realm.where(Story.class).contains("firebaseKey", firebaseKey).findFirst();
//    }
//
//    public Conversation getConversation(String firebaseKey) {
//        return realm.where(Conversation.class).contains("firebaseKey", firebaseKey).findFirst();
//    }
//
//    public StoryProgressPart getStoryProgressPartByQuestionKey(String questionFirebaseKey) {
//        return realm.where(StoryProgressPart.class).contains("questionKey", questionFirebaseKey).findFirst();
//    }
//
//    public StoryProgress getStoryProgress() {
//        return realm.where(StoryProgress.class).findFirst();
//    }
//}
