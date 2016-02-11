import React, { Component } from 'react';
import LoggedIn from '../LoggedIn';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as userActions from '../../actions/UserActions';
import * as groupActions from '../../actions/GroupActions';

class LoggedInContainer extends Component {
  render() {
    const { children, currentUser, groups } = this.props;

    return (
      <LoggedIn
        children={children}
        currentUser={currentUser.user}
        onLogout={this.onLogout.bind(this)}
        groups={groups}
      />
    );
  }

  componentDidMount() {
    const { groupActions, currentUser } = this.props;

    groupActions.getGroups(currentUser.user);
  }

  onLogout() {
    const { userActions, currentUser } = this.props;

    userActions.logout(currentUser.user);
  }
}

export default connect(
  state => ({
    currentUser: state.authentication.currentUser,
    groups: state.groups.groups
  }),
  dispatch => ({
    userActions: bindActionCreators(userActions, dispatch),
    groupActions: bindActionCreators(groupActions, dispatch)
  })
)(LoggedInContainer);
