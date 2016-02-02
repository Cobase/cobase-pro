# CobasePRO REST API

## Registration

Request:

    POST /users

    {
      "username": "user@name.com",
      "password": "password"
    }

Response:

    201 Created

    {
      "id": "bade3a66-5fe7-4724-a085-77b7f063a9b4",
      "username": "user@name.com"
    }

Returns `409 Conflict` if username is already in use.


## Authentication

Request:

    POST /login

    {
      "username": "user@name.com",
      "password": "password"
    }

Response:

    200 OK

    {
      "id": "bade3a66-5fe7-4724-a085-77b7f063a9b4",
      "username": "user@name.com",
      "token": "4e71d768c43b3cf8aeb80845328137a99c4fa796"
    }

Returns `401 Unauthorized` credentials are invalid.

The `token` must be then included in all subsequent request with the header `X-Token`.

For example: `curl -v -H "Content-Type: application/json" -H "Accept: application/json" -H "X-Token: 4e71d768c43b3cf8aeb80845328137a99c4fa796" http://localhost:9000/groups -X GET`


## Groups

### Create new group

Request:

    POST /groups

    {
      "title": "title of the group",
      "tags": "some tags"
    }

Response:

    201 Created

    {
      "id": "c5947522-b743-494c-b947-53f1fedd370e",
      "title": "title of the group",
      "tags": "some tags",
      "isActive": true
    }

### Get groups

Request:

    GET /groups

Response:

    200 OK

    [
      {
        "postCount": 0,
        "id": "c5947522-b743-494c-b947-53f1fedd370e",
        "title": "title of the group"
      }
    ]

### Update existing group

Request:

    PUT /groups/c5947522-b743-494c-b947-53f1fedd370e

    {
      "title": "changed title",
      "tags": "changed tags"
    }

Response:

    200 OK

    {
      "id": "c5947522-b743-494c-b947-53f1fedd370e",
      "title": "changed title",
      "tags": "changed tags",
      "isActive": true
    }

## Posts

### Add post to group

Request:

    POST /groups/c5947522-b743-494c-b947-53f1fedd370e/posts

    {
      "content": "text for the post"
    }

Response:

    201 Created

    {
      "id": "33ef00c6-8c2a-4e24-8f5e-05e992cc0459",
      "groupId": "c5947522-b743-494c-b947-53f1fedd370e",
      "active": true,
      "content": "text for the post",
      "created": "2016-02-01T15:41:59+0200",
      "createdBy": "Artur Losopowski"
    }

### Get posts for group

Request:

    GET /groups/c5947522-b743-494c-b947-53f1fedd370e/posts

Response:

    200 OK

    [
      {
        "id": "33ef00c6-8c2a-4e24-8f5e-05e992cc0459"
        "groupId": "c5947522-b743-494c-b947-53f1fedd370e",
        "active": true,
        "content": "text for the post",
        "created": "2016-01-19T14:33:55+0200",
        "createdBy": "Artur Losopowski"
      }
    ]


### Update post

Request:

    PUT /posts/33ef00c6-8c2a-4e24-8f5e-05e992cc0459

    {
      "content": "updated post"
    }

Response:

    200 OK

    {
      "id": "33ef00c6-8c2a-4e24-8f5e-05e992cc0459",
      "groupId": "c5947522-b743-494c-b947-53f1fedd370e",
      "active": true,
      "content": "updated post",
      "created": "2016-01-19T14:33:55+0200",
      "createdBy": "Artur Losopowski"
    }

### Get posts

Request:

    GET /posts

Response:

    200 OK

    [
      {
        "id": "33ef00c6-8c2a-4e24-8f5e-05e992cc0459"
        "groupId": "c5947522-b743-494c-b947-53f1fedd370e",
        "active": true,
        "content": "text for the post",
        "created": "2016-01-19T14:33:55+0200",
        "createdBy": "Artur Losopowski"
      }
    ]

### Search for posts

Request:

    GET /posts?phrase=something

Response:

    200 OK

    [
      {
        "id": "33ef00c6-8c2a-4e24-8f5e-05e992cc0459"
        "groupId": "c5947522-b743-494c-b947-53f1fedd370e",
        "active": true,
        "content": "something foo bar",
        "created": "2016-01-19T14:33:55+0200",
        "createdBy": "Artur Losopowski"
      }
    ]
