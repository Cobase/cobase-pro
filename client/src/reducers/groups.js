import { List, Map, Record } from 'immutable';
import { combineReducers } from 'redux';

const Group = Record({
  id: null,
  title: null,
  tags: null
});

function groups(state = List(), action) {
  switch (action.type) {
    case 'ADD_GROUP_REQUEST_SUCCESS':
      const { id, title, tags } = action.response.data;

      return state.push(new Group({
        id,
        title,
        tags
      }));

    case 'GET_GROUPS_REQUEST_SUCCESS':
      const { data } = action.response;

      return data.reduce((groups, group) => {
        return groups.push(new Group({
          id: group.id,
          title: group.title,
          tags: group.tags
        }));
      }, state);

    default:
      return state;
  }
}

export default combineReducers({
  groups
});
