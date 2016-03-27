import api from '../util/api';

function addGroupRequest() {
  return {
    type: 'ADD_GROUP_REQUEST'
  };
}

function addGroupRequestSuccess(response) {
  return {
    type: 'ADD_GROUP_REQUEST_SUCCESS',
    response
  };
}

function addGroupRequestFailure(error) {
  return {
    type: 'ADD_GROUP_REQUEST_FAILURE',
    error
  };
}

export function addGroup(user, name, tags) {
  return (dispatch) => {
    dispatch(addGroupRequest());

    api.addGroup(user, name, tags)
      .then(response => dispatch(addGroupRequestSuccess(response)))
      .catch(e => dispatch(addGroupRequestFailure(e)))
  };
}

function getGroupsRequest() {
  return {
    type: 'GET_GROUPS_REQUEST'
  };
}

function getGroupsRequestSuccess(response) {
  return {
    type: 'GET_GROUPS_REQUEST_SUCCESS',
    response
  };
}

function getGroupsRequestFailure(error) {
  return {
    type: 'GET_GROUPS_REQUEST_FAILURE',
    error
  };
}

export function getGroups(user) {
  return (dispatch) => {
    dispatch(getGroupsRequest());

    api.getGroups(user)
      .then(response => dispatch(getGroupsRequestSuccess(response)))
      .catch(e => dispatch(getGroupsRequestFailure(e)))
  };
}
