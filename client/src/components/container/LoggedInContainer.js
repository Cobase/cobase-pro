import React, { Component } from 'react';
import LoggedIn from '../LoggedIn';

import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import * as userActions from '../../actions/UserActions';

class LoggedInContainer extends Component {
  render() {
    const { children, currentUser } = this.props;

    return (
      <LoggedIn
        children={children}
        currentUser={currentUser.user}
        onLogout={this.onLogout.bind(this)}
      />
    );
  }

  onLogout() {
    const { userActions, currentUser } = this.props;

    userActions.logout(currentUser.user);
  }
}

export default connect(
  state => ({
    currentUser: state.authentication.currentUser
  }),
  dispatch => ({
    userActions: bindActionCreators(userActions, dispatch)
  })
)(LoggedInContainer);
