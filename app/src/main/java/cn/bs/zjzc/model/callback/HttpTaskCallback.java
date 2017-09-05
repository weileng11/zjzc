package cn.bs.zjzc.model.callback;

/**
 * Created by Ming on 2016/6/18.
 */
public interface HttpTaskCallback<T> {
    void onTaskFailed(String errorInfo);
    void onTaskSuccess(T data);
}
