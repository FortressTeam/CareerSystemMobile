package com.example.kyler.careersystem;

/**
 * Created by kyler on 18/03/2016.
 */
public class UrlStatic {
//    public static String service="http://192.168.11.195/CareerSystemWebBased/career_system/";
    public static String service="http://u1kkf43607cc.123456798a.koding.io/CareerSystemWebBased/career_system/";
    public static String URLimg = service + "img/";
//    public static String URLLoginToken = service +"api/users/token.json";
    public static String URLSignin = service + "api/users/signin.json";
    public static String URLPosts = service + "api/posts.json";
    public static String URLEditPost = service + "api/posts/";
    public static String URLManagePost = service + "api/posts.json?post_status=1&limite=20&hiring_manager_id=";
    public static String URLHomefragment = service + "api/posts.json?post_status=1&limit=10&page=";
    public static String URLCategories = service + "api/categories.json?limit=100&page=";
    public static String URLFeedbacks = service + "api/feedbacks.json";
    public static String URLApplicant = service + "api/applicants/";
    public static String URLHiringManager = service + "api/hiringmanagers/";
    public static String URLSkillTypes = service + "api/skill_types.json";
    public static String URLSkills = service + "api/skills.json";
    public static String URLPersonalHistory = service + "api/personal_history.json";
    public static String URLPersonalHistory2 = service + "api/personal_history/";
    public static String URLApplicantsHasSkills = service + "api/applicants_has_skills.json";
    public static String URLApplicantsHasHobbies = service + "api/applicants_has_hobbies.json";
    public static String URLHobbies = service + "api/hobbies.json";
//    public static String tokenAccess = "";
}
