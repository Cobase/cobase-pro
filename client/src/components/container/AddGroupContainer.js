import React, { Component } from 'react';
import AddGroup from '../AddGroup';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as groupActions from '../../actions/GroupActions';

class AddGroupContainer extends Component {
  render() {
    return (
      <AddGroup onAddGroup={this.onAddGroup.bind(this)} />
    );
  }

  onAddGroup(title, tags) {
    const { groupActions, currentUser } = this.props;

    groupActions.addGroup(currentUser, title, tags);
  }
}

export default connect(
  state => ({}),
  dispatch => ({
    groupActions: bindActionCreators(groupActions, dispatch)
  })
)(AddGroupContainer);
