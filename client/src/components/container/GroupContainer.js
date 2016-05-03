import React, { PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import Group from '../Group';
import * as groupActionCreators from '../../actions/GroupActions';

const GroupContainer = ({ posts, groupActions, groupId, currentUser }) => (
  <Group
    posts={posts}
    groupActions={groupActions}
    groupId={groupId}
    currentUser={currentUser}
  />
);

export default connect(
  (state, ownProps) => ({
    groupId: ownProps.params.groupId,
    posts: state.posts.posts,
  }),
  dispatch => ({
    groupActions: bindActionCreators(groupActionCreators, dispatch),
  })
)(GroupContainer);

GroupContainer.propTypes = {
  posts: ImmutablePropTypes.list,
  groupActions: PropTypes.object.isRequired,
  groupId: PropTypes.string.isRequired,
  currentUser: ImmutablePropTypes.contains({
    id: PropTypes.string.isRequired,
    username: PropTypes.string.isRequired,
    role: PropTypes.string.isRequired,
    token: PropTypes.string.isRequired,
  }).isRequired,
};
