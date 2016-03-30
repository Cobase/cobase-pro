import { List, Map, Record } from 'immutable';
import { combineReducers } from 'redux';

const Post = Record({
  id: null,
  groupId: null,
  author: null,
  content: null
});

function posts(state = List(), action) {
  switch (action.type) {
    case 'GET_GROUP_POSTS_REQUEST_SUCCESS':
      const { data } = action.response;

      console.log("ACTION RESPONSE:");
      console.log(action.response);

      return data.reduce((posts, post) => {
        return posts.push(new Post({
          id: post.id,
          groupId: post.groupId,
          author: post.author,
          content: post.content
        }));
      }, state);

    default:
      return state;
  }
}

export default combineReducers({
  posts
});
