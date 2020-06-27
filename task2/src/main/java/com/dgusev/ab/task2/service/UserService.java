package com.dgusev.ab.task2.service;

import com.dgusev.ab.task2.kafka.model.Message;
import com.dgusev.ab.task2.model.UserAnalitic;
import com.dgusev.ab.task2.model.UserAnaliticCategorySummary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private Map<String, UserAnalitic> analytics = new HashMap<>();


    public List<UserAnalitic> getAnalitics() {
        return new ArrayList<>(analytics.values());
    }

    public UserAnalitic getByUserId(String userId) {
        return analytics.get(userId);
    }

    public void addPayment(Message message) {
        UserAnalitic user = analytics.get(message.getUserId());
        String category = Integer.toString(message.getCategoryId());
        if (user == null) {
            user = new UserAnalitic();
            user.setTotalSum(message.getAmount());
            user.setUserId(message.getUserId());
            UserAnaliticCategorySummary userAnaliticCategorySummary = new UserAnaliticCategorySummary();
            userAnaliticCategorySummary.setMax(message.getAmount());
            userAnaliticCategorySummary.setMin(message.getAmount());
            userAnaliticCategorySummary.setSum(message.getAmount());
            user.getAnalyticInfo().put(category, userAnaliticCategorySummary);
            analytics.put(message.getUserId(), user);
        } else {
            if (!user.getAnalyticInfo().containsKey(category)) {
                UserAnaliticCategorySummary userAnaliticCategorySummary = new UserAnaliticCategorySummary();
                userAnaliticCategorySummary.setMax(message.getAmount());
                userAnaliticCategorySummary.setMin(message.getAmount());
                userAnaliticCategorySummary.setSum(message.getAmount());
                user.getAnalyticInfo().put(category, userAnaliticCategorySummary);
            } else {
                UserAnaliticCategorySummary userAnaliticCategorySummary = user.getAnalyticInfo().get(category);
                userAnaliticCategorySummary.setSum(userAnaliticCategorySummary.getSum().add(message.getAmount()));
                if (message.getAmount().compareTo(userAnaliticCategorySummary.getMin()) < 0) {
                    userAnaliticCategorySummary.setMin(message.getAmount());
                }
                if (message.getAmount().compareTo(userAnaliticCategorySummary.getMax()) > 0) {
                    userAnaliticCategorySummary.setMax(message.getAmount());
                }
            }
        }

    }
}
