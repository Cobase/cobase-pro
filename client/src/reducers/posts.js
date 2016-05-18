import { List, Record } from 'immutable';
import { combineReducers } from 'redux';

const Post = Record({
  id: null,
  groupId: null,
  createdBy: null,
  message: null,
  time: null,
});

function posts(state = List(), action) {
  switch (action.type) {

    case 'ADD_GROUP_POST_REQUEST_SUCCESS':
      const { id, groupId, content, createdBy, created } = action.response.data;
      return state.unshift(new Post({
        id,
        groupId,
        createdBy,
        message: content,
        time: created,
      }));

    case 'GET_GROUP_POSTS_REQUEST_SUCCESS':
      const receivedPosts = action.response;
      return receivedPosts.data.reduce((groupPosts, post) => (
        groupPosts.push(new Post({
          id: post.id,
          groupId: post.groupId,
          createdBy: post.createdBy,
          message: post.content,
          time: post.created,
        }))
      ), List());

    default:
      return state;
  }
}

export default combineReducers({
  posts,
});
