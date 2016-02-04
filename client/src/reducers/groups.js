import { List, Map } from 'immutable';

const defaultState = Map({
  groups: List()
});

export default function(state = defaultState, action) {
  switch (action.type) {
    default:
      return state;
  }
};
