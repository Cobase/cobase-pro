import { List, Map, Record } from 'immutable';
import { combineReducers } from 'redux';

const Post = Record({
  id: null,
  groupId: null,
  createdBy: null,
  message: null,
  time: null
});

function posts(state = List(), action) {
  switch (action.type) {
    case 'GET_GROUP_POSTS_REQUEST_SUCCESS':
      const { data } = action.response;

      return data.reduce((posts, post) => {
        return posts.push(new Post({
          id: post.id,
          groupId: post.groupId,
          createdBy: post.createdBy,
          message: post.content,
          time: post.created
        }));
      }, List());

    default:
      return state;
  }
}

export default combineReducers({
  posts
});
