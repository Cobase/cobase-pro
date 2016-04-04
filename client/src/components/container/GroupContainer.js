import React, { Component } from 'react';
import PostsList from '../PostsList';
import Group from '../Group';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as groupActions from '../../actions/GroupActions';

class GroupContainer extends Component {
  render() {
    return (
      <div>
        <Group
          posts={this.props.posts}
          groupActions={this.props.groupActions}
          groupId={this.props.groupId}
          currentUser={this.props.currentUser}
        />
      </div>
    );
  }
}

export default connect(
  (state, ownProps) => ({
    groupId: ownProps.params.groupId,
    posts: state.posts.posts
  }),
  dispatch => ({
    groupActions: bindActionCreators(groupActions, dispatch)
  })
)(GroupContainer);
