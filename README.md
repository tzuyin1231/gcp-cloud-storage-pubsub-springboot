## 此專案是結合 Google pub/sub 功能和 cloud storage 的 DEMO side project
### 主要流程
是透過 POST 打 api 傳遞訊息到 pub/sub，並在接收到特定訂閱項目後，進行處理，並將訊息內容儲存到cloud storage
### 執行前的GCP權限設置
1. 須先開設服務帳戶，並設定相關權限( pub/sub & cloud storage 權限)
2. 自身使用者帳戶也需要有Service Account Token Creator的權限 
3. 於終端機，設定預設存取的服務帳戶
<br>$ gcloud auth application-default login --impersonate-service-account SERVICE_ACCT_EMAIL
<br>SERVICE_ACCT_EMAIL 需帶入自己的服務帳戶信箱
### 執行前備妥要使用的GCP服務
可透過GCP console或cli等方式建立服務
1. pub/sub
   - 建立一個topic和他的訂閱項目
2. cloud storage 
   - 開一個bucket
   - 建立一個object
### 執行前的springboot設置異動
1. application.yaml
2. HelloPubSubConsumer 的 subscription() 和 HelloPubSubPublisher 的 topic() 回傳值修正為自己的訂閱項目和主題名稱
