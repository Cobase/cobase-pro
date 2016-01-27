# CobasePRO REST API


### Obtain list of groups

```bash
$ curl -X GET \
       -H "Content-type: application/json" \
       http://localhost:9001/api/groups
```

### Create new group

```bash
$ curl -X POST \
       -H "Content-type: application/json" \
       -d '{"title": "[TITLE_STRING]", "tags": "[TWITTER_HASHTAGS_STRING]"}' \
       http://localhost:9001/api/groups       
```

### Update existing group

```bash
$ curl -X PUT \
       -H "Content-type: application/json" \
       -d '{"title": "[TITLE_STRING]", "tags": "[TWITTER_HASHTAGS_STRING]"}' \
       http://localhost:9001/api/groups/[UUID]       
```