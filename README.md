# CliqInformer
The Github Action is used to Integrate Github and Zoho Cliq by Notifying about the Github Events Performed to the Zoho Cliq Channels.

CliqInformer requires the following inputs to Integrate the Github Actions with your Cliq Channels
- Cliq Webhook Token
- Cliq Channel API Endpoint or Unique Name
- Induividual Messages for the Messages of each of the Github Events (in the name of event-message)
- A default Message which you want to sent if The Message is not Specified for that Event.
- A Non Specific Message which should be
  - none = In case you don't want to send any Message if not Specified
  - custom = In case you have specified the default Message to be Sent if not Specified
  - default = In case you want the default Cliq Informer Message to be Sent to the Cliq Channel
  
Upon Successfully Providing the Inputs as per Criteria, The Message will be Successfully Sent to the Cliq Channel.
