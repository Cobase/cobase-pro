import React, { Component, PropTypes } from 'react';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import LoggedOut from '../LoggedOut';
import * as userActions from '../../actions/UserActions';

class LoggedOutContainer extends Component {
  constructor(props) {
    super(props);

    this.onLogin = this.onLogin.bind(this);
  }

  onLogin(username, password) {
    this.props.userActions.login(username, password);
  }

  render() {
    const { children } = this.props;

    return (
      <LoggedOut
        children={children}
        onLogin={this.onLogin}
      />
    );
  }
}

export default connect(
  () => ({}), // state
  dispatch => ({
    userActions: bindActionCreators(userActions, dispatch),
  })
)(LoggedOutContainer);

LoggedOutContainer.propTypes = {
  userActions: PropTypes.object.isRequired,
  children: PropTypes.element.isRequired,
};
